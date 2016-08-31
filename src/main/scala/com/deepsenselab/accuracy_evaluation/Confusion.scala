package com.deepsenselab.accuracy_evaluation

import scala.util.control.Breaks

/**
  * Created by Ashok K. Pant (ashokpant87@gmail.com) on 8/31/16.
  */
class Confusion {
  private var classes: Int = 0
  private var per = Array.ofDim[Double](classes, 4)
  private var ind = Array.ofDim[String](classes, classes)
  private var cm = Array.ofDim[Int](classes, classes)
  private var samples: Int = 0
  private var c: Double = .0

  def this(classes: Int, samples: Int) {
    this()
    this.classes = classes
    this.samples = samples
  }

  def this(targets: Array[Array[Double]], outputs: Array[Array[Double]]) {
    this()
    confusion(targets, outputs)
  }

  def confusion(targets: Array[Array[Double]], outputs: Array[Array[Double]]): Unit = {
    val numClasses: Int = outputs.length
    setClasses(numClasses)
    if (numClasses == 1) {
      println("Number of classes must be greater than 1.")
      return
    }
    val numSamples: Int = targets(0).length
    setSamples(numSamples)
    var row: Int = 0
    var col: Int = 0
    while (col < numSamples) {
      var max: Double = outputs(0)(col)
      var ind: Int = 0
      var row: Int = 1
      while (row < numClasses) {
        if (outputs(row)(col) > max) {
          max = outputs(row)(col)
          ind = row
        }
        outputs(row)(col) = 0.0
        row += 1
      }
      outputs(0)(col) = 0.0
      outputs(ind)(col) = 1
      col += 1
    }
    var count: Int = 0
    row = 0
    while (row < numClasses) {
      var col: Int = 0
      while (col < numSamples) {
        if (targets(row)(col) != outputs(row)(col))
          count += 1
        col += 1
      }
      row += 1
    }
    val c: Double = count.toDouble / (2 * numSamples).toDouble
    val cm: Array[Array[Int]] = Array.ofDim[Int](numClasses, numClasses)
    row = 0
    while (row < numClasses) {
      var col: Int = 0
      while (col < numClasses) {
        cm(row)(col) = 0
        col += 1
      }
      row += 1
    }
    val i: Array[Int] = new Array[Int](numSamples)
    val j: Array[Int] = new Array[Int](numSamples)
    col = 0
    while (col < numSamples) {
      var row: Int = 0
      val break = new Breaks()
      break.breakable({
        while (row < numClasses) {
          if (targets(row)(col) == 1.0) {
            i(col) = row
            break.break()
          }
          row += 1
        }
      })
      col += 1
    }
    col = 0
    while (col < numSamples) {
      var row: Int = 0
      val break = new Breaks()
      break.breakable({
        while (row < numClasses) {
          if (outputs(row)(col) == 1.0) {
            j(col) = row
            break.break()
          }
          row += 1
        }
      })

      col += 1
    }
    col = 0
    while (col < numSamples) {
      cm(i(col))(j(col)) = cm(i(col))(j(col)) + 1
      col += 1
    }
    val ind: Array[Array[String]] = Array.ofDim[String](numClasses, numClasses)
    row = 0
    while (row < numClasses) {
      var col: Int = 0
      while (col < numClasses) {
        ind(row)(col) = ""
        col += 1
      }
      row += 1
    }
    col = 0
    while (col < numSamples) {
      if (ind(i(col))(j(col)) == "") ind(i(col))(j(col)) = new StringBuffer().append(col).toString
      else ind(i(col))(j(col)) = new StringBuffer().append(ind(i(col))(j(col))).append(",").append(col).toString
      col += 1
    }
    val per: Array[Array[Double]] = Array.ofDim[Double](numClasses, 4)
    row = 0
    while (row < numClasses) {
      var col: Int = 0
      while (col < 4) {
        per(row)(col) = 0.0
        col += 1
      }
      row += 1
    }
    row = 0
    while (row < numClasses) {
      val yi: Array[Double] = new Array[Double](numSamples)
      val ti: Array[Double] = new Array[Double](numSamples)
      var col: Int = 0
      while (col < numSamples) {
        {
          yi(col) = outputs(row)(col)
          ti(col) = targets(row)(col)
        }
        col += 1
      }
      var a: Int = 0
      var b: Int = 0
      col = 0
      while (col < numSamples) {
        {
          if (yi(col) != 1 && ti(col) == 1) a = a + 1
          if (yi(col) != 1) b = b + 1
        }
        col += 1
      }
      per(row)(0) = a.toDouble / b.toDouble
      a = 0
      b = 0
      col = 0
      while (col < numSamples) {
        {
          if (yi(col) == 1 && ti(col) != 1) a = a + 1
          if (yi(col) == 1) b = b + 1
        }
        col += 1
      }
      per(row)(1) = a.toDouble / b.toDouble
      a = 0
      b = 0
      col = 0
      while (col < numSamples) {
        {
          if (yi(col) == 1 && ti(col) == 1) a = a + 1
          if (yi(col) == 1) b = b + 1
        }
        col += 1
      }
      per(row)(2) = a.toDouble / b.toDouble
      a = 0
      b = 0
      col = 0
      while (col < numSamples) {
        if (yi(col) != 1 && ti(col) != 1) a = a + 1
        if (yi(col) != 1) b = b + 1
        col += 1
      }
      per(row)(3) = a.toDouble / b.toDouble
      row += 1
    }
    row = 0
    while (row < numClasses) {
      var col: Int = 0
      while (col < 4) {
        if (per(row)(col).isNaN) per(row)(col) = 0.0
        col += 1
      }
      row += 1
    }
    setC(round(c, 2))
    setCM(cm)
    setInd(ind)
    setPer(per)
  }

  def setC(c: Double) {
    this.c = c
  }

  def setCM(cm: Array[Array[Int]]) {
    this.cm = cm
  }

  def setInd(ind: Array[Array[String]]) {
    this.ind = ind
  }

  def setPer(per: Array[Array[Double]]) {
    this.per = per
  }

  def setClasses(classes: Int) {
    this.classes = classes
  }

  def setSamples(samples: Int) {
    this.samples = samples
  }

  def getC: Double = {
    c
  }

  def getInd: Array[Array[String]] = {
    ind
  }

  def getPer: Array[Array[Double]] = {
    per
  }

  def getSamples: Int = {
    samples
  }

  def print() {
    println("Confusion Results")
    println("=======================================")
    printC()
    printCM()
    printInd()
    printPer()
  }

  def printC() {
    println("\tConfusion value\n\t\tc = " + c)
  }

  def printCM() {
    println("\tConfusion Matrix")
    var row: Int = 0
    while (row < getClasses) {
      System.out.print("\t\t")
      var col: Int = 0
      while (col < getClasses) {
        System.out.print(getCM(row)(col) + " ")
        col += 1
      }
      println()
      row += 1
    }
  }

  def getCM: Array[Array[Int]] = {
    cm
  }

  def getClasses: Int = {
    classes
  }

  def printInd() {
    println("\tIndices")
    var row: Int = 0
    while (row < classes) {
      var col: Int = 0
      while (col < classes) {
        System.out.print("\t\t[" + ind(row)(col) + "]")
        col += 1
      }
      println()
      row += 1
    }
  }

  def printPer() {
    println("\tPercentages")
    var row: Int = 0
    while (row < classes) {
      System.out.print("\t\t")
      var col: Int = 0
      while (col < 4) {
        System.out.print(round(per(row)(col), 2) + " ")
        col += 1

      }
      println
      row += 1
    }
  }

  def round(valueToRound: Double, numberOfDecimalPlaces: Int): Double = {
    val multiplicationFactor: Double = Math.pow(10, numberOfDecimalPlaces)
    val interestedInZeroDPs: Double = valueToRound * multiplicationFactor
    interestedInZeroDPs.round / multiplicationFactor
  }
}
