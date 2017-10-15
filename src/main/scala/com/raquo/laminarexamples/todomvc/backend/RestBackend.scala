package com.raquo.laminarexamples.todomvc.backend

import com.raquo.xstream.XStream

trait RestBackend[Model] {

  import RestBackend._

  val $request: XStream[Request[Model]]

  val $response: XStream[Response[Model]]

  lazy val $listResponse: XStream[ListResponse[Model]] = $response.collect {
    case response: ListResponse[Model @unchecked] => response
  }

  lazy val $readResponse: XStream[ReadResponse[Model]] = $response.collect {
    case response: ReadResponse[Model @unchecked] => response
  }

  lazy val $createResponse: XStream[CreateResponse[Model]] = $response.collect {
    case response: CreateResponse[Model @unchecked] => response
  }

  lazy val $updateResponse: XStream[UpdateResponse[Model]] = $response.collect {
    case response: UpdateResponse[Model @unchecked] => response
  }

  lazy val $deleteResponse: XStream[DeleteResponse[Model]] = $response.collect {
    case response: DeleteResponse[Model @unchecked] => response
  }
}

object RestBackend {

  sealed trait Request[Model]

  case class ListRequest[Model]() extends Request[Model]
  case class ReadRequest[Model](id: Int) extends Request[Model]
  case class CreateRequest[Model](model: Model) extends Request[Model]
  case class UpdateRequest[Model](model: Model) extends Request[Model]
  case class DeleteRequest[Model](model: Model) extends Request[Model]

  // @TODO add failure case

  sealed trait Response[Model] {
    val request: Request[Model]
  }

  case class ListResponse[Model](request: Request[Model], tasks: Iterable[Model]) extends Response[Model]

  sealed trait ItemResponse[Model] extends Response[Model] {
    val model: Model
  }

  case class ReadResponse[Model](request: Request[Model], model: Model) extends Response[Model]
  case class CreateResponse[Model](request: Request[Model], model: Model) extends Response[Model]
  case class UpdateResponse[Model](request: Request[Model], model: Model) extends Response[Model]
  case class DeleteResponse[Model](request: Request[Model], model: Model) extends Response[Model]
}
