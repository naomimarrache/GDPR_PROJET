package fr.esme.gdpr.services

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SaveMode}

object Service3 {
  def getClientData(df: DataFrame, id: String, idColumnName: String, writePath: String): Unit = {
    df.filter(col(idColumnName) === id).write.mode(SaveMode.Overwrite).csv(writePath + "_" + id)
  }
}