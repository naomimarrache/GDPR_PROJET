package fr.esme.gdpr
import fr.esme.gdpr.services.{Service1, Service2, Service3}
import org.apache.spark.sql.SparkSession

object Launcher {
  def main(args: Array[String]): Unit = {
    //Add Scopt command line
    val serviceToLaunch = "s2"

    val session = SparkSession.builder().master("local").getOrCreate()
    val df = session.read.option("header", true).option("inferSchema", true).option("delimiter",";").csv("input_data/file1.csv")
    serviceToLaunch match {
      case "s1" => Service1.deleteLineWithId(df, "11224", "ID").show
      case "s2" => Service2.hashIdColumn(df, "Nom", "11224", "ID").show
      case "s3" => Service3.getClientData(df, "11224", "ID", "output_data/serive3_output")
    }
  }
}