package com.nao.samples.config

import java.io.{File, _}

import org.apache.spark.sql.DataFrame
import spray.json.DefaultJsonProtocol._
import spray.json._


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