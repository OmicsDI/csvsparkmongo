package org.ddi.omics.org.ddi.omics.itraits

import org.apache.spark.sql.DataFrame

trait ReadFile {

  def getData(path:String):DataFrame

}
