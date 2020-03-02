import React, { useEffect, useState } from "react";
import "./App.css";
import { TextField, Typography, Grid, Button } from "@material-ui/core";

export default function App() {
  const [code, setCode] = useState("");
  const [output, setOutput] = useState("");

  useEffect(() => {
    var rawFile = new XMLHttpRequest();
    rawFile.open("GET", "./defaultCodes/HelloWorld.java", false);
    rawFile.onreadystatechange = function() {
      if (rawFile.readyState === 4) {
        if (rawFile.status === 200 || rawFile.status === 0) {
          var allText = rawFile.responseText;
          setCode(allText);
        }
      }
    };
    rawFile.send(null);
  }, []);

  const compile = () => {
    fetch("http://localhost:8080/java", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        code: code
      })
    })
      .then(response => {
        console.log(response);
        return response.json();
      })
      .then(data => {
        setOutput(data.output);
      });
  };

  return (
    <div className="App">
      <Grid container direction="column" aligItems="center" spacing={4}>
        <Grid item xs>
          <Typography
            variant="h3"
            style={{ color: "white", textTransform: "uppercase" }}
          >
            Beste kompilatoren
          </Typography>
        </Grid>
        <Grid item xs>
          <TextField
            variant="filled"
            multiline
            rows={10}
            label="Code"
            style={{ width: "80%", background: "white" }}
            value={code}
            onChange={e => setCode(e.target.value)}
          ></TextField>
        </Grid>
        <Grid item xs>
          <Button
            variant="outlined"
            color="secondary"
            style={{ color: "white" }}
            onClick={compile}
          >
            Kompiler
          </Button>
        </Grid>
        <Grid item xs>
          <TextField
            variant="filled"
            multiline
            label="Output"
            style={{ width: "80%", background: "white" }}
            value={output}
          ></TextField>
        </Grid>
      </Grid>
    </div>
  );
}
