import fr.esme.gdpr.DataFrameReader
import fr.esme.gdpr.configuration.JsonConfigProtocol._
import fr.esme.gdpr.configuration.{ConfigReader, JsonConfig}
import fr.esme.gdpr.services.{Service1, Service2, Service3}
import fr.esme.gdpr.utils.schemas.DataFrameSchema
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType
import spray.json._

object Launcher {

  def use_service(service: String): Unit ={
    //Add Scopt command line

    val serviceToLaunch = service

    val session = SparkSession.builder().master("local").getOrCreate()
    val df = session.read.option("header", true).option("inferSchema", true).option("delimiter",";").csv("input_data/file1.csv")
    serviceToLaunch match {
      case "s1" => Service1.deleteLineWithId(df, "11224", "ID").show
      case "s2" => Service2.hashIdColumn(df, "Nom", "11224", "ID").show
      case "s3" => Service3.getClientData(df, "11224", "ID", "output_data/serive3_output")
    }
  }


  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)

    use_service("s2")


    implicit val session = SparkSession.builder().master("local").getOrCreate()

    val confReader = ConfigReader.readConfig("conf/config.json")
    val configuration = confReader.parseJson.convertTo[JsonConfig]
    val dfSchema: StructType = DataFrameSchema.buildDataframeSchema(configuration.fields)
    val data = DataFrameReader.readCsv("data.csv", configuration.csvOptions, dfSchema)
    data.printSchema()
    data.show


  }
}