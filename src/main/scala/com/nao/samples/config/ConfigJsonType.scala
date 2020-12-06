package com.fakir.samples.config

import spray.json._
import DefaultJsonProtocol._
import java.io.File
import java.io._

import com.fakir.samples.config.ConfigParser
import com.fakir.samples.utils.SparkReaderWriter
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Dataset, SaveMode, SparkSession}

object ConfigJsonType {

  def create_json_config_type(df: DataFrame,filename:String): Unit = {
    df.printSchema()
    val columnType = df.schema.fields.toSeq
    case class Colonne(nameColumn: String, typeColumn: String)
    // create the formats and provide them implicitly
    implicit val colonneFormat = jsonFormat2(Colonne)
    val jsonCol = columnType.map(col=>Colonne(col.name,col.dataType.toString)).toJson.prettyPrint
    println(jsonCol)
    val writer = new PrintWriter(new File(filename ))
    try{
      writer.write(jsonCol)
      println("fichier config json cree")
    }
    writer.close()
  }
}