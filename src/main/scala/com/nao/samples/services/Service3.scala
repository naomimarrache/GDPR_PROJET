package com.nao.samples.services

import com.nao.samples.utils.SparkReaderWriter
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col


object Service3{

  def service3(df:DataFrame,id:String, outputPath:String,outputFormat:String): Unit ={

    val dfFilterId = df.filter(col("ID") === id)
    dfFilterId.show

    SparkReaderWriter.writeData(dfFilterId, outputPath, outputFormat, true)

  }


}