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
)
