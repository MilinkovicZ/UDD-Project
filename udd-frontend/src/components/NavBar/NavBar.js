import React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Button from "@mui/material/Button";
import { Link } from "react-router-dom";

export default function NavBar() {
    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static" sx={{ backgroundColor: "#141e30" }}>
                <Toolbar sx={{ justifyContent: "space-between" }}>
                    <Box sx={{ display: "flex", alignItems: "center" }}>
                        <Link to="/">
                            <img
                                src="./logo.png"
                                alt="Logo"
                                style={{
                                    height: "50px",
                                    width: "50px",
                                    marginRight: "10px",
                                }}
                            />
                        </Link>
                    </Box>
                    <Box>
                        <Button
                            color="inherit"
                            component={Link}
                            to="/contracts"
                        >
                            Contracts
                        </Button>
                        <Button color="inherit" component={Link} to="/laws">
                            Laws
                        </Button>
                        <Button color="inherit" component={Link} to="/upload">
                            Upload
                        </Button>
                    </Box>
                </Toolbar>
            </AppBar>
        </Box>
    );
}
