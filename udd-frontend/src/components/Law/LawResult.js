import React from "react";
import {
    Card,
    CardContent,
    Typography,
    Button,
    CardActions,
} from "@mui/material";
import { downloadDocument } from "../../services/SearchService";

export default function LawResult({ result }) {
    const handleDownload = async (filename) => {
        await downloadDocument(filename);
        alert("Successfully downloaded!");
    };

    return (
        <Card
            style={{
                backgroundColor: "#141e30",
                color: "#cccccc",
                margin: "20px",
                width: "300px",
                height: "250px",
                display: "inline-block",
                verticalAlign: "top",
            }}
        >
            <CardContent>
                <Typography variant="h6" style={{ marginTop: "10px" }}>
                    Filename: {result.filename}
                </Typography>
                <Typography variant="body1" style={{ marginTop: "10px" }}>
                    Highlight: {result.highlight}
                </Typography>
            </CardContent>
            <CardActions style={{display: "flex",
                flexDirection: "column", marginTop: "auto" }}>
                <Button
                    variant="contained"
                    style={{ background: "#00008b" }}
                    onClick={() => handleDownload(result.filename)}
                >
                    Download
                </Button>
            </CardActions>
        </Card>
    );
}
