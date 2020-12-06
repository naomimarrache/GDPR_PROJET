package com.fakir.samples.services
import spray.json._
import DefaultJsonProtocol._
import java.io.{File, FileNotFoundException, _}

import com.fakir.samples.config.ConfigJsonType
import com.fakir.samples.config.ConfigParser
import com.fakir.samples.utils.SparkReaderWriter
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Dataset, SaveMode, SparkSession}

import scala.collection.mutable.ArrayBuffer
import org.apache.spark.sql.functions.to_json


object mapping_type {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    val configCli = ConfigParser.getConfigArgs(args)
    //val sparkSession = SparkSession.builder().master("local").getOrCreate()
    val df = SparkReaderWriter.readData(configCli.inputPath, configCli.inputFormat, true)
    df.show

    ConfigJsonType.create_json_config_type(df,"output_data/test_config_json.json")

  }
}
