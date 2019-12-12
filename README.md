# MyDukan-Assignment

### ASSIGNMENT 1
Call localhost:8080/file/import -: This is just a dummy call, which will import all the data from Excel file to Elastic Search Engine
An index with the name of **"ayush_group"** is created on the designate Elastic Search end point.
The mapping of the index is as below :
{ 
   "ayush_group":{ 
      "aliases":{ 

      },
      "mappings":{ 
         "group_type":{ 
            "properties":{ 
               "description":{ 
                  "type":"text",
                  "fields":{ 
                     "keyword":{ 
                        "type":"keyword",
                        "ignore_above":256
                     }
                  }
               },
               "isActive":{ 
                  "type":"text",
                  "fields":{ 
                     "keyword":{ 
                        "type":"keyword",
                        "ignore_above":256
                     }
                  }
               },
               "name":{ 
                  "type":"text",
                  "fields":{ 
                     "keyword":{ 
                        "type":"keyword",
                        "ignore_above":256
                     }
                  }
               },
               "products":{ 
                  "properties":{ 
                     "cost":{ 
                        "type":"float"
                     },
                     "groupAssociated":{ 
                        "properties":{ 
                           "name":{ 
                              "type":"text",
                              "fields":{ 
                                 "keyword":{ 
                                    "type":"keyword",
                                    "ignore_above":256
                                 }
                              }
                           }
                        }
                     },
                     "modelName":{ 
                        "type":"text",
                        "fields":{ 
                           "keyword":{ 
                              "type":"keyword",
                              "ignore_above":256
                           }
                        }
                     },
                     "name":{ 
                        "type":"text",
                        "fields":{ 
                           "keyword":{ 
                              "type":"keyword",
                              "ignore_above":256
                           }
                        }
                     },
                     "serialNo":{ 
                        "type":"long"
                     }
                  }
               }
            }
         }
      },
      "settings":{ 
         "index":{ 
            "creation_date":"1576096646163",
            "number_of_shards":"5",
            "number_of_replicas":"1",
            "uuid":"9ZSvoSOuQnS7K_U0xysyaQ",
            "version":{ 
               "created":"6030299"
            },
            "provided_name":"ayush_group"
         }
      }
   }
}


### ASSIGNMENT 2

Used Elastic Search REST High Level client to perform all data operations on the Elastic Search Engine via Spring Boot

Follwing are the APIs present in the application :

**GET GROUP BY NAME**

Relative path : /elastic_search/getGroupFromName

HTTP Method Type : POST

Input Parameter : groupName(Name of the group)

Description : This call will return the group if data related to the group is found


**UPDATE PRODUCT PRICE**

Relative path : /elastic_search/updateProductPrice

HTTP Method Type : PUT

Input Parameter : productSerialNo (Serial Number of the product whose price is to be updated),
				  groupName (Name of Prduct's Group), 
				  price (Price to be updated)

Description : This call will update the price of the product whose serial number has been provided



**ADD PRODUCT**

Relative path : /elastic_search/addProduct

HTTP Method Type : POST

Input Parameter : AddProductDTO (custom object) eg : 
{
		"product":{
			"serialNo": Long,
            "name": String,
            "cost": Double,
            "modelName": String
		},
		"group":{
			"name": String,
            "description":String,
            "isActive": String
		}
}

Description : This call will add a new product to elastic search engine in the group corresponding to it if the group does not exists then group will first be 	created in Elastic Search engine and then  the product will be associated to it.


**CHANGE PRODUCT GROUP**

Relative path : /elastic_search/changeProductGroup

HTTP Method Type : POST

Input Parameter : productSerialNo (Serial Number of the product whose price is to be updated),
				  currentGroupName (Name of Prduct's current Group), 
				  newGroupName (Name of new Group in which product needs to be moved)

Description : This call will change the group of the product and if the new group does not exist exception will be thrown


**GET ALL GROUPS**

Relative path : /elastic_search/getAllGroups

HTTP Method Type : POST

Input Parameter : from (indicates the starting range),
				  size (indicates the total number of documents needed)

Description : This call will get all the groups present in the elastic search index based the from and size values


[df1]
> The connection string can be set from application.properties :

> elasticsearch.host : sets the elastic search host

> elasticsearch.host.port : sets the port on which elastic search is hosted

