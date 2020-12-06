package com.nao.samples.services

import com.nao.samples.utils.{Encoder, SparkReaderWriter}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, udf, when}


object Service2{

  val EncodField = udf((elem: String) => {
    Encoder.sha1(elem)
  })

  def service2(df:DataFrame,id:String,outputPath:String,outputFormat:String): Unit ={
    val Encod_user_data_Df = df.withColumn("Nom", when(col("ID")===id,EncodField(col("Nom"))).otherwise(col("Nom")))
      .withColumn("Prenom", when(col("ID")===id,EncodField(col("Prenom"))).otherwise(col("Prenom")))
    Encod_user_data_Df.show
    SparkReaderWriter.writeData(Encod_user_data_Df, outputPath, outputFormat, true)
  }

}