package org.ddi.omics.service

import com.mongodb.casbah.Imports.{$set, MongoClient, MongoClientURI, MongoDBObject}
import org.ddi.omics.model.Dataset
import org.ddi.omics.org.ddi.omics.itraits.BaseService
import org.ddi.omics.utils.Constants
import org.mongodb.scala.bson.BsonDocument

object MongoSparkService extends BaseService{

  val mongoClientURI = MongoClientURI(Constants.prodMongoUri)

  val mongoClient =  MongoClient.apply(mongoClientURI)

  val db = mongoClient(Constants.mongoDatabase)

  val coll = db(Constants.mongoCollection)

  def save(dataset:DatasetScore): Int ={
    val query = MongoDBObject(Constants.accession -> dataset.accession, Constants.datasetDatabase -> dataset.database)
    val update = $set(Constants.additionalSearchScaled -> Set(dataset.ajs_connectivity_score))
    val result = coll.update( query, update )
    println("Number updated: " + result.getN)
    result.getN
  }
}
