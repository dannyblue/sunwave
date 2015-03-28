var ws = null;
var url = null;
var transports = [];

function setConnected(connected) {
}

function connect() {
    if (!url) {
//        alert('Select whether to use W3C WebSocket or SockJS');
        return;
    }

    ws = (url.indexOf('sockjs') != -1) ? 
        new SockJS(url, undefined, {protocols_whitelist: transports}) : new WebSocket(url);

    ws.onopen = function () {
        setConnected(true);
//        log('Info: connection opened.');
    };
    ws.onmessage = function (event) {
        log('Received: ' + event.data);
    };
    ws.onclose = function (event) {
        setConnected(false);
//        log('Info: connection closed.');
//        log(event);
    };
}

function disconnect() {
    if (ws != null) {
        ws.close();
        ws = null;
    }
    setConnected(false);
}

function echo() {
    if (ws != null) {
        var message = "testMessage";
//        log('Sent: ' + message);
        ws.send(message);
    } else {
        alert('connection not established, please connect.');
    }
}

function updateUrl(urlPath) {
    if (urlPath.indexOf('sockjs') != -1) {
        url = urlPath;
        document.getElementById('sockJsTransportSelect').style.visibility = 'visible';
    }
    else {
      if (window.location.protocol == 'http:') {
          url = 'ws://' + window.location.host + urlPath;
      } else {
          url = 'wss://' + window.location.host + urlPath;
      }
      document.getElementById('sockJsTransportSelect').style.visibility = 'hidden';
    }
}

function updateTransport(transport) {
  transports = (transport == 'all') ?  [] : [transport];
}

function log(message) {
    alert(message);
}