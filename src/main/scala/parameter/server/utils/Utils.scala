package parameter.server.utils

import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector
import org.apache.flink.api.common.typeinfo.TypeInformation

object Utils {

  /**
    * Split a DataStream of Either to two separate stream
    * @param ds
    * @tparam A
    * @tparam B
    * @return DataStream of the left values, DataStream of the right values
    */
  def splitStream[A,B]
  (ds: DataStream[Either[A,B]])  (implicit 
  tiA: TypeInformation[A],
  tiB: TypeInformation[B]) : (DataStream[A], DataStream[B]) = 
    (ds.flatMap[A]((value: Either[A,B], out: Collector[A]) => {
      value match {
        case Left(aa) =>
          out.collect(aa)
        case Right(_) =>
      }
    }),
    ds.flatMap[B]((value: Either[A,B], out: Collector[B]) => {
      value match {
        case Right(bb) =>
          out.collect(bb)
        case Left(_) =>
      }
    }))

}
