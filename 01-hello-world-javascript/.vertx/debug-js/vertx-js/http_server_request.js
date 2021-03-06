/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

/** @module vertx-js/http_server_request */
var utils = require('vertx-js/util/utils');
var ServerWebSocket = require('vertx-js/server_web_socket');
var HttpServerFileUpload = require('vertx-js/http_server_file_upload');
var Buffer = require('vertx-js/buffer');
var HttpFrame = require('vertx-js/http_frame');
var HttpServerResponse = require('vertx-js/http_server_response');
var MultiMap = require('vertx-js/multi_map');
var ReadStream = require('vertx-js/read_stream');
var HttpConnection = require('vertx-js/http_connection');
var SocketAddress = require('vertx-js/socket_address');
var NetSocket = require('vertx-js/net_socket');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JHttpServerRequest = Java.type('io.vertx.core.http.HttpServerRequest');

/**
 Represents a server-side HTTP request.
 <p>
 Instances are created for each request and passed to the user via a handler.
 <p>
 @class
*/
var HttpServerRequest = function(j_val) {

  var j_httpServerRequest = j_val;
  var that = this;
  ReadStream.call(this, j_val);

  /**

   @public
   @param handler {function} 
   @return {HttpServerRequest}
   */
  this.exceptionHandler = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && (typeof __args[0] === 'function' || __args[0] == null)) {
      j_httpServerRequest["exceptionHandler(io.vertx.core.Handler)"](handler == null ? null : function(jVal) {
      handler(utils.convReturnThrowable(jVal));
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param handler {function} 
   @return {HttpServerRequest}
   */
  this.handler = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && (typeof __args[0] === 'function' || __args[0] == null)) {
      j_httpServerRequest["handler(io.vertx.core.Handler)"](handler == null ? null : function(jVal) {
      handler(utils.convReturnVertxGen(Buffer, jVal));
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {HttpServerRequest}
   */
  this.pause = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_httpServerRequest["pause()"]();
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {HttpServerRequest}
   */
  this.resume = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_httpServerRequest["resume()"]();
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param endHandler {function} 
   @return {HttpServerRequest}
   */
  this.endHandler = function(endHandler) {
    var __args = arguments;
    if (__args.length === 1 && (typeof __args[0] === 'function' || __args[0] == null)) {
      j_httpServerRequest["endHandler(io.vertx.core.Handler)"](endHandler);
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {Object} the HTTP version of the request
   */
  this.version = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return utils.convReturnEnum(j_httpServerRequest["version()"]());
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {Object} the HTTP method for the request.
   */
  this.method = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return utils.convReturnEnum(j_httpServerRequest["method()"]());
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {string} the HTTP method as sent by the client
   */
  this.rawMethod = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_httpServerRequest["rawMethod()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {boolean} true if this {@link NetSocket} is encrypted via SSL/TLS
   */
  this.isSSL = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_httpServerRequest["isSSL()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {string} the scheme of the request
   */
  this.scheme = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_httpServerRequest["scheme()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {string} the URI of the request. This is usually a relative URI
   */
  this.uri = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_httpServerRequest["uri()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {string} The path part of the uri. For example /somepath/somemorepath/someresource.foo
   */
  this.path = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_httpServerRequest["path()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {string} the query part of the uri. For example someparam=32&amp;someotherparam=x
   */
  this.query = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_httpServerRequest["query()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {string} the request host. For HTTP2 it returns the  pseudo header otherwise it returns the  header
   */
  this.host = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_httpServerRequest["host()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {HttpServerResponse} the response. Each instance of this class has an {@link HttpServerResponse} instance attached to it. This is used to send the response back to the client.
   */
  this.response = function() {
    var __args = arguments;
    if (__args.length === 0) {
      if (that.cachedresponse == null) {
        that.cachedresponse = utils.convReturnVertxGen(HttpServerResponse, j_httpServerRequest["response()"]());
      }
      return that.cachedresponse;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {MultiMap} the headers in the request.
   */
  this.headers = function() {
    var __args = arguments;
    if (__args.length === 0) {
      if (that.cachedheaders == null) {
        that.cachedheaders = utils.convReturnVertxGen(MultiMap, j_httpServerRequest["headers()"]());
      }
      return that.cachedheaders;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Return the first header value with the specified name

   @public
   @param headerName {string} the header name 
   @return {string} the header value
   */
  this.getHeader = function(headerName) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'string') {
      return j_httpServerRequest["getHeader(java.lang.String)"](headerName);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {MultiMap} the query parameters in the request
   */
  this.params = function() {
    var __args = arguments;
    if (__args.length === 0) {
      if (that.cachedparams == null) {
        that.cachedparams = utils.convReturnVertxGen(MultiMap, j_httpServerRequest["params()"]());
      }
      return that.cachedparams;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Return the first param value with the specified name

   @public
   @param paramName {string} the param name 
   @return {string} the param value
   */
  this.getParam = function(paramName) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'string') {
      return j_httpServerRequest["getParam(java.lang.String)"](paramName);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {SocketAddress} the remote (client side) address of the request
   */
  this.remoteAddress = function() {
    var __args = arguments;
    if (__args.length === 0) {
      if (that.cachedremoteAddress == null) {
        that.cachedremoteAddress = utils.convReturnVertxGen(SocketAddress, j_httpServerRequest["remoteAddress()"]());
      }
      return that.cachedremoteAddress;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {SocketAddress} the local (server side) address of the server that handles the request
   */
  this.localAddress = function() {
    var __args = arguments;
    if (__args.length === 0) {
      if (that.cachedlocalAddress == null) {
        that.cachedlocalAddress = utils.convReturnVertxGen(SocketAddress, j_httpServerRequest["localAddress()"]());
      }
      return that.cachedlocalAddress;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {string} the absolute URI corresponding to the the HTTP request
   */
  this.absoluteURI = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_httpServerRequest["absoluteURI()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Convenience method for receiving the entire request body in one piece.
   <p>
   This saves the user having to manually setting a data and end handler and append the chunks of the body until
   the whole body received. Don't use this if your request body is large - you could potentially run out of RAM.

   @public
   @param bodyHandler {function} This handler will be called after all the body has been received 
   @return {HttpServerRequest}
   */
  this.bodyHandler = function(bodyHandler) {
    var __args = arguments;
    if (__args.length === 1 && (typeof __args[0] === 'function' || __args[0] == null)) {
      j_httpServerRequest["bodyHandler(io.vertx.core.Handler)"](bodyHandler == null ? null : function(jVal) {
      bodyHandler(utils.convReturnVertxGen(Buffer, jVal));
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Get a net socket for the underlying connection of this request.
   <p>
   USE THIS WITH CAUTION!
   <p>
   Once you have called this method, you must handle writing to the connection yourself using the net socket,
   the server request instance will no longer be usable as normal.
   Writing to the socket directly if you don't know what you're doing can easily break the HTTP protocol.

   @public

   @return {NetSocket} the net socket
   */
  this.netSocket = function() {
    var __args = arguments;
    if (__args.length === 0) {
      if (that.cachednetSocket == null) {
        that.cachednetSocket = utils.convReturnVertxGen(NetSocket, j_httpServerRequest["netSocket()"]());
      }
      return that.cachednetSocket;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Call this with true if you are expecting a multi-part body to be submitted in the request.
   This must be called before the body of the request has been received

   @public
   @param expect {boolean} true - if you are expecting a multi-part body 
   @return {HttpServerRequest} a reference to this, so the API can be used fluently
   */
  this.setExpectMultipart = function(expect) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] ==='boolean') {
      j_httpServerRequest["setExpectMultipart(boolean)"](expect);
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {boolean} true if we are expecting a multi-part body for this request. See {@link HttpServerRequest#setExpectMultipart}.
   */
  this.isExpectMultipart = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_httpServerRequest["isExpectMultipart()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Set an upload handler. The handler will get notified once a new file upload was received to allow you to deal
   with the file upload.

   @public
   @param uploadHandler {function} 
   @return {HttpServerRequest} a reference to this, so the API can be used fluently
   */
  this.uploadHandler = function(uploadHandler) {
    var __args = arguments;
    if (__args.length === 1 && (typeof __args[0] === 'function' || __args[0] == null)) {
      j_httpServerRequest["uploadHandler(io.vertx.core.Handler)"](uploadHandler == null ? null : function(jVal) {
      uploadHandler(utils.convReturnVertxGen(HttpServerFileUpload, jVal));
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Returns a map of all form attributes in the request.
   <p>
   Be aware that the attributes will only be available after the whole body has been received, i.e. after
   the request end handler has been called.
   <p>
   {@link HttpServerRequest#setExpectMultipart} must be called first before trying to get the form attributes.

   @public

   @return {MultiMap} the form attributes
   */
  this.formAttributes = function() {
    var __args = arguments;
    if (__args.length === 0) {
      if (that.cachedformAttributes == null) {
        that.cachedformAttributes = utils.convReturnVertxGen(MultiMap, j_httpServerRequest["formAttributes()"]());
      }
      return that.cachedformAttributes;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Return the first form attribute value with the specified name

   @public
   @param attributeName {string} the attribute name 
   @return {string} the attribute value
   */
  this.getFormAttribute = function(attributeName) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'string') {
      return j_httpServerRequest["getFormAttribute(java.lang.String)"](attributeName);
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Upgrade the connection to a WebSocket connection.
   <p>
   This is an alternative way of handling WebSockets and can only be used if no websocket handlers are set on the
   Http server, and can only be used during the upgrade request during the WebSocket handshake.

   @public

   @return {ServerWebSocket} the WebSocket
   */
  this.upgrade = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return utils.convReturnVertxGen(ServerWebSocket, j_httpServerRequest["upgrade()"]());
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Has the request ended? I.e. has the entire request, including the body been read?

   @public

   @return {boolean} true if ended
   */
  this.isEnded = function() {
    var __args = arguments;
    if (__args.length === 0) {
      return j_httpServerRequest["isEnded()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   Set a custom frame handler. The handler will get notified when the http stream receives an custom HTTP/2
   frame. HTTP/2 permits extension of the protocol.

   @public
   @param handler {function} 
   @return {HttpServerRequest} a reference to this, so the API can be used fluently
   */
  this.customFrameHandler = function(handler) {
    var __args = arguments;
    if (__args.length === 1 && typeof __args[0] === 'function') {
      j_httpServerRequest["customFrameHandler(io.vertx.core.Handler)"](function(jVal) {
      handler(utils.convReturnVertxGen(HttpFrame, jVal));
    });
      return that;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   @return {HttpConnection} the {@link HttpConnection} associated with this request
   */
  this.connection = function() {
    var __args = arguments;
    if (__args.length === 0) {
      if (that.cachedconnection == null) {
        that.cachedconnection = utils.convReturnVertxGen(HttpConnection, j_httpServerRequest["connection()"]());
      }
      return that.cachedconnection;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_httpServerRequest;
};

HttpServerRequest._jclass = utils.getJavaClass("io.vertx.core.http.HttpServerRequest");
HttpServerRequest._jtype = {
  accept: function(obj) {
    return HttpServerRequest._jclass.isInstance(obj._jdel);
  },
  wrap: function(jdel) {
    var obj = Object.create(HttpServerRequest.prototype, {});
    HttpServerRequest.apply(obj, arguments);
    return obj;
  },
  unwrap: function(obj) {
    return obj._jdel;
  }
};
HttpServerRequest._create = function(jdel) {
  var obj = Object.create(HttpServerRequest.prototype, {});
  HttpServerRequest.apply(obj, arguments);
  return obj;
}
module.exports = HttpServerRequest;