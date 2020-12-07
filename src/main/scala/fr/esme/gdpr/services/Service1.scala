package fr.esme.gdpr.services

import org.apache.spark.sql.DataFrame

object Service1 {
  def deleteLineWithId(df: DataFrame, id: String, idColumnName: String): DataFrame = {
    import org.apache.spark.sql.functions._
    df.filter(col(idColumnName) =!= id)
  }
}

