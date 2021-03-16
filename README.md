# elasticsearch

#### 介绍
elasticsearch索引设计及查询实例

#### 技术栈
springboot、elasticsearch、RestHighLevelClient


#### 安装教程
1.  下载elasticsearch-6.7.0并解压
https://mirrors.huaweicloud.com/elasticsearch/6.7.0/elasticsearch-6.7.0.zip

2.  修改elasticsearch-6.7.0\config\jvm.options堆内存大小
-Xms1g
-Xmx1g

3.  修改elasticsearch-6.7.0\config\elasticsearch.yml配置项
network.host: 0.0.0.0
http.port: 9200
http.cors.enabled: true
http.cors.allow-origin: "*"
action.auto_create_index: true

4.  下载并安装ik分词器
https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.7.0/elasticsearch-analysis-ik-6.7.0.zip
解压至elasticsearch-6.7.0\plugins\elasticsearch-analysis-ik-6.7.0

5.  windows下启动es，双击启动
elasticsearch-6.7.0\bin\elasticsearch.bat


#### 创建template或者index(二选一)
##### put http://127.0.0.1:9200/_template/cat_template

`
{
    "template": "cat*",
    "order": 1,
    "settings": {
        "number_of_shards": 2,
        "number_of_replicas": 1,
        "analysis": {
            "analyzer": {
                "ngram_analyzer": {
                    "tokenizer": "ngram_tokenizer"
                },
                "comma_analyzer": {
                    "type": "pattern",
                    "pattern": ","
                }
            },
            "tokenizer": {
                "ngram_tokenizer": {
                    "type": "ngram",
                    "min_gram": 1,
                    "max_gram": 33,
                    "token_chars": [
                        "letter",
                        "digit"
                    ]
                }
            }
        },
        "index": {
            "max_result_window": 100000000
        }
    },
    "mappings": {
        "_doc": {
            "properties": {
                "catId": {
                    "type": "long"
                },
                "catName": {
                    "type": "text",
                    "analyzer": "ngram_analyzer",
                    "fields": {
                        "comma": {
                            "type": "text",
                            "fielddata": true,
                            "analyzer": "comma_analyzer"
                        },
                        "maxWord": {
                            "type": "text",
                            "analyzer": "ik_max_word"
                        }
                    }
                },
                "tag": {
                    "type": "text",
                    "analyzer": "comma_analyzer"
                },
                "description": {
                    "type": "text",
                    "analyzer": "ik_max_word",
                    "fields": {
                        "comma": {
                            "type": "text",
                            "fielddata": true,
                            "analyzer": "comma_analyzer"
                        },
                        "ngram": {
                            "type": "text",
                            "analyzer": "ngram_analyzer"
                        }
                    }
                },
                "age": {
                    "type": "integer"
                },
                "deleted": {
                    "type": "integer"
                },
                "status": {
                    "type": "integer"
                },
                "birthday": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss"
                },
                "homeTime": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss"
                },
                "createTime": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss"
                }
            }
        }
    },
    "aliases": {
        "cat": {}
    }
}
`

##### put http://127.0.0.1:9200/cat

`
{
    "settings": {
        "number_of_shards": 2,
        "number_of_replicas": 1,
        "analysis": {
            "analyzer": {
                "ngram_analyzer": {
                    "tokenizer": "ngram_tokenizer"
                },
                "comma_analyzer": {
                    "type": "pattern",
                    "pattern": ","
                }
            },
            "tokenizer": {
                "ngram_tokenizer": {
                    "type": "ngram",
                    "min_gram": 1,
                    "max_gram": 33,
                    "token_chars": [
                        "letter",
                        "digit"
                    ]
                }
            }
        },
        "index": {
            "max_result_window": 100000000
        }
    },
    "mappings": {
        "_doc": {
            "properties": {
                "catId": {
                    "type": "long"
                },
                "catName": {
                    "type": "text",
                    "analyzer": "ngram_analyzer",
                    "fields": {
                        "comma": {
                            "type": "text",
                            "fielddata": true,
                            "analyzer": "comma_analyzer"
                        },
                        "maxWord": {
                            "type": "text",
                            "analyzer": "ik_max_word"
                        }
                    }
                },
                "tag": {
                    "type": "text",
                    "analyzer": "comma_analyzer"
                },
                "description": {
                    "type": "text",
                    "analyzer": "ik_max_word",
                    "fields": {
                        "comma": {
                            "type": "text",
                            "fielddata": true,
                            "analyzer": "comma_analyzer"
                        },
                        "ngram": {
                            "type": "text",
                            "analyzer": "ngram_analyzer"
                        }
                    }
                },
                "age": {
                    "type": "integer"
                },
                "deleted": {
                    "type": "integer"
                },
                "status": {
                    "type": "integer"
                },
                "birthday": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss"
                },
                "homeTime": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss"
                },
                "createTime": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss"
                }
            }
        }
    }
}
`
#### 写入测试数据
##### post http://127.0.0.1:9200/cat/_doc

`{
     "catId": 1,
     "catName": "安久拉",
     "tag": "猫,动物,花猫",
     "description": "黄白花猫",
     "age": 5,
     "deleted": 0,
     "status": 1,
     "birthday": "2015-02-01 14:20:52",
     "homeTime": "2016-01-01 14:20:53",
     "createTime": "2021-03-01 14:20:32"
 }`
 
 `{
      "catId": 2,
      "catName": "白玉",
      "tag": "猫,白猫",
      "description": "白猫，实际上是一只黑猫",
      "age": 4,
      "deleted": 0,
      "status": 1,
      "birthday": "2016-12-01 14:20:52",
      "homeTime": "2017-01-01 14:20:53",
      "createTime": "2021-03-01 15:20:32"
  }`

 `{
      "catId": 3,
      "catName": "拿铁",
      "tag": "猫,橘猫",
      "description": "是我心爱的小拿铁呀",
      "age": 3,
      "deleted": 0,
      "status": 1,
      "birthday": "2017-03-01 14:20:52",
      "homeTime": "2017-03-01 14:20:52",
      "createTime": "2021-03-01 15:20:32"
  }`
  
   `{
        "catId": 4,
        "catName": "海棠",
        "tag": "猫,花猫",
        "description": "玳瑁，三花猫",
        "age": 3,
        "deleted": 0,
        "status": 1,
        "birthday": "2017-03-01 14:20:52",
        "homeTime": "2017-03-01 14:20:52",
        "createTime": "2021-03-01 15:20:32"
    }`
    
  `{
        "catId": 5,
        "catName": "想想",
        "tag": "猫,黑猫,黑白花猫",
        "description": "黑猫，白手套",
        "age": 1,
        "deleted": 0,
        "status": 1,
        "birthday": "2019-02-01 14:20:52",
        "homeTime": "2019-02-01 14:20:52",
        "createTime": "2021-03-01 15:20:32"
    }`
#### 查询
##### post http://127.0.0.1:9200/cat/_search

`
{
    "size": 10,
    "from": 0,
    "query": {
        "bool": {
            "must": [
                {
                    "term": {
                        "catName": "安久拉"
                    }
                },
                {
                    "term": {
                        "tag": {
                            "value": "猫"
                        }
                    }
                },
                {
                    "match": {
                        "description": "猫"
                    }
                },
                {
                    "range": {
                        "birthday": {
                            "gte": "2010-07-04 00:00:00",
                            "lt": "2021-12-07 00:00:00"
                        }
                    }
                }
            ]
        }
    },
    "sort": {
        "createTime": {
            "order": "desc"
        }
    }
}
`

#### 使用RestHighLevelClient 查询 （demo接口）
##### post http://localhost:9999/elastic/getCatList

`
{
     "pageNum":1,
     "pageSize":10,
     "catName":"安"
 }
`