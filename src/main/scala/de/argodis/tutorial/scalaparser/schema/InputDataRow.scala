package de.argodis.tutorial.scalaparser.schema

case class InputDataRow (
  vin: String,
  readout_date: String,
  brand: String,
  model: String,
  mileage: Int,
  coolant_temperature: Double,
  oil_pressure: Double,
  ignition_cycles: Int,
  tyre_pressure: Double
) {
  def inputMap: Map[Int, Double] = Map(
    5 -> mileage.toDouble,
    6 -> coolant_temperature,
    7 -> oil_pressure,
    8 -> ignition_cycles.toDouble,
    9 -> tyre_pressure
  )
}
