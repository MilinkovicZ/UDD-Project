import React, { useState } from "react";
import {
    Container,
    Typography,
    Input,
    Button,
    Grid,
    InputLabel,
} from "@mui/material";
import { uploadDocuments } from "../../services/SearchService";

const UploadPage = () => {
    const [contractFile, setContractFile] = useState(null);
    const [lawFiles, setLawFiles] = useState([]);

    const handleContractChange = (event) => {
        const file = event.target.files[0];
        setContractFile(file);
    };

    const handleLawChange = (event) => {
        const files = event.target.files;
        setLawFiles(Array.from(files));
    };

    const handleUpload = async () => {
        if (!contractFile) {
            alert("Contract file is required.");
            return;
        }

        if (lawFiles.length === 0) {
            alert("At least one law file is required.");
            return;
        }

        await uploadDocuments({
            contract: contractFile,
            laws: lawFiles,
        });

        alert('Successfully added new documents!');
        window.location.reload();
    };

    return (
        <Container maxWidth="100%" style={{ marginTop: "20px" }}>
            <Typography
                variant="h3"
                align="left"
                gutterBottom
                style={{ color: "#00008b", marginLeft: "20px" }}
            >
                Upload Documents
            </Typography>

            <Grid container spacing={2} style={{ marginLeft: "10px" }}>
                <Grid item xs={12}>
                    <InputLabel
                        htmlFor="contract-file"
                        style={{ color: "#cccccc", fontSize: "20px" }}
                    >
                        Contract:
                    </InputLabel>
                    <Input
                        type="file"
                        accept=".pdf"
                        onChange={handleContractChange}
                        id="contract-file"
                        style={{ color: "#cccccc" }}
                    />
                </Grid>
                <Grid item xs={12}>
                    <InputLabel
                        htmlFor="law-files"
                        style={{ color: "#cccccc", fontSize: "20px" }}
                    >
                        Laws:
                    </InputLabel>
                    <Input
                        type="file"
                        accept=".pdf"
                        multiple
                        onChange={handleLawChange}
                        id="law-files"
                        style={{ color: "#cccccc" }}
                    />
                </Grid>
                <Grid item xs={12}>
                    <Button
                        variant="contained"
                        onClick={handleUpload}
                        style={{
                            backgroundColor: "#00008b",
                            color: "#f0f0f0",
                            borderRadius: "50px",
                            padding: "15px 30px",
                            marginTop: "20px",
                            marginLeft: "5%",
                        }}
                    >
                        Upload
                    </Button>
                </Grid>
            </Grid>
        </Container>
    );
};

export default UploadPage;
