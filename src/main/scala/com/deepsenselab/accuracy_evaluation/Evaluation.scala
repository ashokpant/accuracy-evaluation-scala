package com.deepsenselab.accuracy_evaluation

/**
  * Created by Ashok K. Pant (ashokpant87@gmail.com) on 8/31/16.
  */
class Evaluation {
  private var avgAccuray: Double = .0
  private var errRate: Double = .0
  private var precisionMicro: Double = .0
  private var recallMicro: Double = .0
  private var fscoreMicro: Double = .0
  private var precisionMacro: Double = .0
  private var recallMacro: Double = .0
  private var fscoreMacro: Double = .0

  def this(confusion: Confusion) {
    this()
    avgAccuray = 0.0
    errRate = 0.0
    precisionMicro = 0.0
    recallMicro = 0.0
    fscoreMicro = 0.0
    precisionMacro = 0.0
    recallMacro = 0.0
    fscoreMacro = 0.0
    evaluation(confusion)
  }

  def evaluation(confusion: Confusion) {
    val per: Array[Array[Double]] = confusion.getPer
    val numClasses: Int = confusion.getClasses
    var avgAccuracy: Double = 0.0
    var fn: Double = 0.0
    var fp: Double = 0.0
    var tp: Double = 0.0
    var tn: Double = 0.0
    var i: Int = 0
    while (i < numClasses) {
        fn = per(i)(0)
        fp = per(i)(1)
        tp = per(i)(2)
        tn = per(i)(3)
        avgAccuracy = +avgAccuracy + ((tp + tn) / (tp + fn + fp + tn))
        i += 1; i - 1
    }
    avgAccuracy = avgAccuracy / numClasses
    var errRate: Double = 0.0
    i = 0
    while (i < numClasses) {
        fn = per(i)(0)
        fp = per(i)(1)
        tp = per(i)(2)
        tn = per(i)(3)
        errRate = +errRate + ((fp + fn) / (tp + fn + fp + tn))
        i += 1; i - 1
    }
    errRate = errRate / numClasses
    var numerator: Double = 0.0
    var denominator: Double = 0.0
   i = 0
    while (i < numClasses) {
        fn = per(i)(0)
        fp = per(i)(1)
        tp = per(i)(2)
        tn = per(i)(3)
        numerator = numerator + tp
        denominator = denominator + (tp + fp)
        i += 1; i - 1
    }
    val precisionMicro: Double = numerator / denominator
    numerator = 0.0
    denominator = 0.0
    i = 0
    while (i < numClasses) {
        fn = per(i)(0)
        fp = per(i)(1)
        tp = per(i)(2)
        tn = per(i)(3)
        numerator = numerator + tp
        denominator = denominator + (tp + fn)
        i += 1; i - 1
    }
    val recallMicro: Double = numerator / denominator
    var beta: Double = 1
    numerator = (Math.pow(beta, 2) + 1) * precisionMicro * recallMicro
    denominator = Math.pow(beta, 2) * precisionMicro + recallMicro
    val fscoreMicro: Double = numerator / denominator
    var precisionMacro: Double = 0.0
    i = 0
    while (i < numClasses) {
        fn = per(i)(0)
        fp = per(i)(1)
        tp = per(i)(2)
        tn = per(i)(3)
        precisionMacro = precisionMacro + (tp / (tp + fp))
        i += 1; i - 1
    }
    precisionMacro = precisionMacro / numClasses
    var recallMacro: Double = 0.0
     i = 0
    while (i < numClasses) {
        fn = per(i)(0)
        fp = per(i)(1)
        tp = per(i)(2)
        tn = per(i)(3)
        recallMacro = recallMacro + (tp / (tp + fn))
        i += 1; i - 1
    }
    recallMacro = recallMacro / numClasses
    beta = 1
    numerator = (Math.pow(beta, 2) + 1) * precisionMacro * recallMacro
    denominator = Math.pow(beta, 2) * precisionMacro + recallMacro
    val fscoreMacro: Double = numerator / denominator
    setAvgAccuray(confusion.round(avgAccuracy, 4))
    setErrRate(confusion.round(errRate, 4))
    setPrecisionMicro(confusion.round(precisionMicro, 4))
    setRecallMicro(confusion.round(recallMicro, 4))
    setFscoreMicro(confusion.round(fscoreMicro, 4))
    setPrecisionMacro(confusion.round(precisionMacro, 4))
    setRecallMacro(confusion.round(recallMacro, 4))
    setFscoreMacro(confusion.round(fscoreMacro, 4))
  }

  def getAvgAccuray: Double = {
     avgAccuray
  }

  def setAvgAccuray(avgAccuray: Double) {
    this.avgAccuray = avgAccuray
  }

  def getErrRate: Double = {
     errRate
  }

  def setErrRate(errRate: Double) {
    this.errRate = errRate
  }

  def getPrecisionMicro: Double = {
     precisionMicro
  }

  def setPrecisionMicro(precisionMicro: Double) {
    this.precisionMicro = precisionMicro
  }

  def getRecallMicro: Double = {
     recallMicro
  }

  def setRecallMicro(recallMicro: Double) {
    this.recallMicro = recallMicro
  }

  def getFscoreMicro: Double = {
     fscoreMicro
  }

  def setFscoreMicro(fscoreMicro: Double) {
    this.fscoreMicro = fscoreMicro
  }

  def getPrecisionMacro: Double = {
     precisionMacro
  }

  def setPrecisionMacro(precisionMacro: Double) {
    this.precisionMacro = precisionMacro
  }

  def getRecallMacro: Double = {
     recallMacro
  }

  def setRecallMacro(recallMacro: Double) {
    this.recallMacro = recallMacro
  }

  def getFscoreMacro: Double = {
     fscoreMacro
  }

  def setFscoreMacro(fscoreMacro: Double) {
    this.fscoreMacro = fscoreMacro
  }

  def print() {
    println("\nAccuracy Evaluation Results")
    println("=======================================")
    println("\tAverage Accuracy(%)       : " + getAvgAccuray * 100)
    println("\tError(%)                  : " + getErrRate * 100)
    println("\tPrecision (Micro)(%)      : " + getPrecisionMicro * 100)
    println("\tRecall (Micro)(%)         : " + getRecallMicro * 100)
    println("\tFscore (Micro)(%)         : " + getFscoreMicro * 100)
    println("\tPrecision (Macro)(%)      : " + getPrecisionMacro * 100)
    println("\tRecall (Macro)(%)         : " + getRecallMacro * 100)
    println("\tFscore (Macro)(%)         : " + getFscoreMacro * 100)
  }
}
