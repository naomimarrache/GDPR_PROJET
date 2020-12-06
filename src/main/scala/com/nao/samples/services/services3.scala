package com.fakir.samples.services

import java.io.File
import java.io._
import com.fakir.samples.config.ConfigParser
import com.fakir.samples.utils.SparkReaderWriter
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SaveMode, SparkSession}

object services3 {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)

    val configCli = ConfigParser.getConfigArgs(args)

    val df = SparkReaderWriter.readData(configCli.inputPath, configCli.inputFormat, true)
    df.show

    val dfFilterId = df.filter(col("ID") === configCli.id)
    dfFilterId.show

    //if the name of the columns has a space, replace it by _
    val columsWithoutSpaces = df.columns.map(elem => elem.replaceAll(" ","_"))
    val dfWithoutIdWithRightColumnNames = dfFilterId.toDF(columsWithoutSpaces:_*)

    dfWithoutIdWithRightColumnNames.show
    SparkReaderWriter.writeData(
      dfFilterId, configCli.outputPath, configCli.outputFormat, true)

    //dfWithoutId.write.mode(SaveMode.Overwrite).csv("test_output")


  }
}
