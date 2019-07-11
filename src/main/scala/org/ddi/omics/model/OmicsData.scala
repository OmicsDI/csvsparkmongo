package org.ddi.omics.model

class OmicsData {

}

case class CsvFields(accession:String, database:String, omicsType:Array[String],
                     species:Array[String], tissue:Array[String], disease:Array[String], citationCount:Int,
                     reanalysisCount:Int, searchDomain:Array[String], domainCount:Int,
                     ensembl:Array[String],uniprot:Array[String],viewCount:Int,searchCount:Int,downloadCount:Int
                    )

case class Dataset(accession:String, database: String, connections:String,
                   reanalysis:String = "", view:String = "", citation:String = "",download:String = "")