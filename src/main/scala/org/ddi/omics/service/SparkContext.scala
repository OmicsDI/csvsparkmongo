package org.ddi.omics.service

import org.apache.spark.sql.{SQLContext, SparkSession}
import org.ddi.omics.org.ddi.omics.itraits.BaseContext
import org.ddi.omics.utils.Constants

object SparkContext extends BaseContext{

  val sparkSession = SparkSession.builder()
    .master(Constants.sparkMasterUrl)
    .appName(Constants.sparkAppName)
    .config(Constants.mongoInputUriKey, Constants.prodMongoUri)
    .config(Constants.mongOutUriKey, Constants.prodMongoUri)
    .getOrCreate()
  sparkSession.conf.set("spark.driver.memory","6g")
  sparkSession.conf.set("spark.executor.memory", "5g")

  def getSparkSession():SparkSession = {
    sparkSession
  }

  def getSqlContext():SQLContext = {
    getSparkSession().sqlContext
  }

}
