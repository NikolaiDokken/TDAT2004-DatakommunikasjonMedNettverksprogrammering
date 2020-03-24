$(document).ready(function() {
  var wSocket = new WebSocket("ws://localhost:3001");

  wSocket.onerror = error => {
    $("#connection_label").html("Not connected");
  };

  wSocket.onopen = () => {
    $("#connection_label").html("Connected");
  };

  wSocket.onmessage = event => {
    var jsonMessage = JSON.parse(event.data);

    var username = jsonMessage.username;
    var message = jsonMessage.message;
    var messageColor = jsonMessage.message_color;
    var sendDate = jsonMessage.send_date;
    var newMessage = "[" + username + " (" + sendDate + ")]: " + message;
    var newMessageElement =
      '<p class="user-message" style="color:' +
      messageColor +
      '">' +
      newMessage +
      "</p>";
    $("#message-board").append(newMessageElement);
  };

  wSocket.onclose = function(message) {
    $("#connection_label").html("Not connected");
  };

  $("#new-message-button").click(function() {
    if (wSocket.readyState == 1) {
      var now = new Date();
      var sendDate = now.getHours() + ":" + now.getMinutes();
      var username = $("#username-input").val();
      if (username === "") {
        username = "Anonym";
      }
      var jsonMessage = {
        username: username,
        message: $("#new-message").val(),
        message_color: $("#color-input").val(),
        send_date: sendDate
      };

      wSocket.send(JSON.stringify(jsonMessage));
      $("#new-message").val("");
    }
  });

  $("#new-message").keypress(function(event) {
    if (event.keyCode == 13 && $(this).val() !== "") {
      $("#new-message-button").click();
    }
  });
});
