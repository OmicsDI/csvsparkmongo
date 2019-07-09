package org.ddi.omics.service

import org.apache.spark.sql.{DataFrame, SQLContext}
import org.ddi.omics.org.ddi.omics.itraits.ReadFile
import org.apache.spark.sql.Encoders

class ReadCsvFile extends ReadFile{

  override def getData(path: String):DataFrame ={
    val sqlContext = SparkContext.getSqlContext()
    readCsv(path, sqlContext)
  }

  def readCsv(filePath:String,sc:SQLContext): DataFrame =
  {
    val csvData = sc.read.format("csv")
      .option("header", "true")//.schema(schema)
      .option("inferSchema",true)
      .option("delimiter", "\t")
      .load(filePath)

    csvData
  }
}

object ReadCsvFile {

  def main(args:Array[String]):Unit ={
    val sqlContext = SparkContext.getSqlContext()
    import sqlContext.implicits._
    val personCreator: (String, String, Double) => DatasetScore = DatasetScore.apply _
    val readCsvFile = new ReadCsvFile
    val scoreEncoder = Encoders.product[DatasetScore]
    val csvdata = readCsvFile.getData("/home/gaur/Downloads/aj-normalised-connectivity-score-2018-09-23.txt")
    val scores = csvdata.select("accession","database","ajs_connectivity_score").na.drop()
      .as(scoreEncoder)

    scores.printSchema()
    println(scores.takeAsList(3).size())
    //
    scores.map(dt => {
      println(dt)
      MongoSparkService.save(dt)
    ""
    }).count()
  }
}

case class DatasetScore(accession:String, database:String, ajs_connectivity_score:Double)

/*object DatasetScore{

  def apply(accession: String, database: String, ajs_connectivity_score: Double): DatasetScore =
    new DatasetScore(accession, database, ajs_connectivity_score)

  //def apply(): DatasetScore = new DatasetScore("testacc", "testdb", 0.0)
}*/


