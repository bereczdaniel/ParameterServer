package parameter.server.algorithms.matrix.factorization
import matrix.factorization.initializer.RangedRandomFactorInitializerDescriptor
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import parameter.server.ParameterServerSkeleton
import parameter.server.algorithms.matrix.factorization.RecSysMessages.EvaluationRequest
import parameter.server.algorithms.matrix.factorization.impl.{KafkaMfPsFactory, KafkaRedisMfPsFactory, RedisMfPsFactory}


trait MfPsFactory {

  def createPs(generalMfProperties: GeneralMfProperties,
                        parameters: ParameterTool, factorInitDesc: RangedRandomFactorInitializerDescriptor,
                        inputStream: DataStream[EvaluationRequest], env: StreamExecutionEnvironment): ParameterServerSkeleton[EvaluationRequest]

}

object MfPsFactory {

  /** Get the logging backend db
    *
    * @return DbWriter
    */
  def createPs(psImplType: String,
               parameters: ParameterTool,
               inputStream: DataStream[EvaluationRequest], env: StreamExecutionEnvironment): ParameterServerSkeleton[EvaluationRequest] = {
    val generalParam = parseGeneralParameters(parameters)
    (psImplType match {
      case "kafka" => new KafkaMfPsFactory
      case "kafkaredis" => new KafkaRedisMfPsFactory
      case "redis" => new RedisMfPsFactory
      case _ => throw new UnsupportedOperationException
    }).createPs(generalParam,
      parameters, RangedRandomFactorInitializerDescriptor(generalParam.numFactors, generalParam.randomInitRangeMin, generalParam.randomInitRangeMax),
      inputStream, env)
  }

  def parseGeneralParameters(parameters: ParameterTool): GeneralMfProperties = {
    val learningRate = parameters.getDouble("learningRate")
    val negativeSampleRate = parameters.getInt("negativeSampleRate")
    val numFactors = parameters.getInt("numFactors")
    val rangeMin = parameters.getDouble("randomInitRangeMin")
    val rangeMax = parameters.getDouble("randomInitRangeMax")
    val workerK = parameters.getInt("workerK")
    val bucketSize = parameters.getInt("bucketSize")

    GeneralMfProperties(learningRate, numFactors, negativeSampleRate, rangeMin, rangeMax,
                     workerK, bucketSize)
  }

}
