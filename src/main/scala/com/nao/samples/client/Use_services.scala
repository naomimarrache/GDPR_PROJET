package com.nao.samples.client

import com.nao.samples.config.{ConfigJsonType, ConfigParser}
import com.nao.samples.services.{Service1, Service2, Service3}
import com.nao.samples.utils.SparkReaderWriter
import org.apache.log4j.{Level, Logger}

object Use_services{

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    val configCli = ConfigParser.getConfigArgs(args)
    val df = SparkReaderWriter.readData(configCli.inputPath, configCli.inputFormat, true)


    Service1.service1(df,configCli.id,"output_data/service1",configCli.inputFormat)
    Service2.service2(df,configCli.id,"output_data/service2",configCli.inputFormat)
    Service3.service3(df,configCli.id,"output_data/service3",configCli.inputFormat)


    ConfigJsonType.create_json_config_type(df,"output_data/test_config_json.json")


    //Service1.service1(df,configCli.id,configCli.outputPath,configCli.inputFormat)
    //Service2.service2(df,configCli.id,configCli.outputPath,configCli.inputFormat)
    //Service3.service3(df,configCli.id,configCli.outputPath,configCli.inputFormat)





  }

}