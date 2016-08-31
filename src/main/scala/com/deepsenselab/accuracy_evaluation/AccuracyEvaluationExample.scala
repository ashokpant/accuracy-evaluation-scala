package com.deepsenselab.accuracy_evaluation

/**
  * Created by Ashok K. Pant (ashokpant87@gmail.com) on 8/31/16.
  */
object AccuracyEvaluationExample {
  def main(args: Array[String]) {
    val targets: Array[Array[Double]] = Array(
      Array(1, 1, 0, 0, 0, 0),
      Array(0, 0, 1, 1, 0, 0),
      Array(0, 0, 0, 0, 1, 1)
    )
    val outputs: Array[Array[Double]] = Array(
      Array(0.1, 0.86, 0.2, 0.1, .02, 0.1),
      Array(0.4, 0.12, 0.768, 0.145, 0.1, 0.8),
      Array(0.454, 0.35, 0.21, 0.0, 0.89, 0.9999)
    )
    val confusion: Confusion = new Confusion(targets, outputs)
    confusion.print()
    val evaluation: Evaluation = new Evaluation(confusion)
    evaluation.print()
  }
}
