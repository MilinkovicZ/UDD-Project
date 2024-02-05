import React from "react";
import NavBar from "./components/NavBar/NavBar";
import AppRoutes from "./routes/AppRoutes";
import { Box } from "@mui/material";

function App() {
    return (
        <React.Fragment>
            <Box sx={{ backgroundColor: "#375573", minHeight: "100vh" }}>
                <NavBar />
                <AppRoutes />
            </Box>
        </React.Fragment>
    );
}

export default App;
