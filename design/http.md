#### concepts
* message: 

        generic-message = start-line
                          *(message-header CRLF)
                          CRLF
                          [ message-body ]
        start-line      = Request-Line | Status-Line
        
    * request
        * method: GET, POST, PUT, DELETE
        * URI
        * version
        * MIME-like message ( request modifiers, client info, optional body content)
    * response
        * status line (protocol version, success/error code)
            * status codes
            
      - 1xx: Informational - Request received, continuing process

      - 2xx: Success - The action was successfully received,
        understood, and accepted

      - 3xx: Redirection - Further action must be taken in order to
        complete the request

      - 4xx: Client Error - The request contains bad syntax or cannot
        be fulfilled

      - 5xx: Server Error - The server failed to fulfill an apparently
        valid request
                    
        * MIME-like message (server info, entity metainfo, possible entity body)
        
    * entity body = f(message body). f is entity encoding.
    
* actors
    * User agent
    * origin server
    * intermediaries. Can have cache to shortcut communcations.
        * proxy: rewrite, forward request
        * gateway: receiving agent, alayer above other servers, may translate requests.
        * tunnel: relay point between two connections, not changing messages.
* connections. In http 1.0, a new conn for each exchange. http1.1, can reuse same conn.
    * persistent connections
        * saving on CPU in routers and hosts, memory for tcp stack
        * pipelining requests/response to lower elapsed time.
        * network congestion
        * cheaper retry (not closing connections on errors)

* access authentication
    * basic
    * digest
    
* content negotiation
    * server driven
        * wasterful, client must speecify pref in each request
        * server doesn't know
    * client driver
        * need a second request
    * transparent negotiation: combo of both