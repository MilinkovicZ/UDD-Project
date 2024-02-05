import React from "react";
import {
    Card,
    CardContent,
    Typography,
    Button,
    CardActions,
} from "@mui/material";
import { downloadDocument } from "../../services/SearchService";

export default function ContractResult({ result }) {
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
                display: "inline-block",
                verticalAlign: "top",
            }}
        >
            <CardContent>
                <Typography variant="h6" gutterBottom>
                    {result.governmentName}
                </Typography>
                <Typography variant="body2" color="#cccccc" gutterBottom>
                    Government Level: {result.governmentLevel}
                </Typography>
                <Typography variant="body2" color="#cccccc" gutterBottom>
                    Signatory: {result.signatoryPersonName}{" "}
                    {result.signatoryPersonSurname}
                </Typography>
                <Typography variant="body1" style={{ marginTop: "10px" }}>
                    Highlight: {result.highlight}
                </Typography>
            </CardContent>
            <CardActions>
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
