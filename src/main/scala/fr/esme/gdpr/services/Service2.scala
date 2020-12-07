
package fr.esme.gdpr.services

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._


object Service2 {

  def hashString = udf((name: String) => {
    import java.math.BigInteger
    import java.security.MessageDigest
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(name.getBytes)
    val bigInt = new BigInteger(1,digest)
    val hashedString = bigInt.toString(16)
    hashedString
  })

  def filterLineWithId(df: DataFrame, id: String, idColumnName: String): DataFrame = {
    import org.apache.spark.sql.functions._
    df.filter(col(idColumnName) === id)
  }

  def hashIdColumn(df: DataFrame, columnNameToHash: String, id: String, idColumnName: String): DataFrame = {
    val filteredDF = filterLineWithId(df, id, idColumnName)
    filteredDF.withColumn(columnNameToHash, hashString(col(columnNameToHash)))
  }

  def hashIdColumns(df: DataFrame, columnNamesToHash: Seq[String]): DataFrame = {

    columnNamesToHash.foldLeft(df)((accumulator, current) => accumulator.withColumn(current, hashString(col(current))))

  }

}