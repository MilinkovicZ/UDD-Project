import React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Button from "@mui/material/Button";

export default function NavBar() {
  const handleContractsClick = async () => {
    console.log("Contracts");
  };

  const handelLawsClick = async () => {
    console.log("Laws");
  };

  const handleUploadClick = async () => {
    console.log("Upload");
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static" sx={{ backgroundColor: "#141e30" }}>
        <Toolbar sx={{ justifyContent: "flex-end" }}>
          <Button color="inherit" onClick={handleContractsClick}>
            Contracts
          </Button>
          <Button color="inherit" onClick={handelLawsClick}>
            Laws
          </Button>
          <Button color="inherit" onClick={handleUploadClick}>
            Upload
          </Button>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
