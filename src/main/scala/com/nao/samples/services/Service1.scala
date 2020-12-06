package com.nao.samples.services

import com.nao.samples.utils.SparkReaderWriter
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col


object Service1{

  def service1(df:DataFrame,id:String,outputPath:String,outputFormat:String): Unit ={
    val dfWithoutId = df.filter(col("ID") =!= id)
    dfWithoutId.show
    SparkReaderWriter.writeData( dfWithoutId, outputPath, outputFormat, true)
  }

}