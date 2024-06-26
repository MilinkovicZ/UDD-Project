import { useState } from "react";
import {
    Container,
    Typography,
    Button,
    FormControl,
    InputLabel,
    OutlinedInput,
    Grid,
} from "@mui/material";
import { searchLaws } from "../../services/SearchService";
import LawResult from "./LawResult";

export default function LawPage() {
    const [inputValue, setInputValue] = useState("");
    const [results, setResults] = useState([]);

    const handleOnSearch = async () => {
        if (!inputValue.trim()) {
            setInputValue("");
            return;
        }

        const response = await searchLaws(inputValue);
        setResults(response.results);
    };

    return (
        <Container
            maxWidth="100%"
            style={{ marginTop: "20px", marginLeft: "20px", paddingLeft: "0" }}
        >
            <Typography
                variant="h3"
                align="left"
                gutterBottom
                style={{ color: "#00008b", marginLeft: "20px" }}
            >
                Search Laws
            </Typography>

            <FormControl
                fullWidth
                variant="outlined"
                style={{ marginLeft: "20px", width: "50%" }}
            >
                <InputLabel htmlFor="input-field">Law content</InputLabel>
                <OutlinedInput
                    id={`input-field`}
                    label="Input Field"
                    value={inputValue}
                    onChange={(e) => setInputValue(e.target.value)}
                    style={{ color: "#cccccc" }}
                />
            </FormControl>

            <Grid container style={{ marginTop: "20px" }}>
                <Button
                    variant="contained"
                    onClick={handleOnSearch}
                    style={{
                        backgroundColor: "#00008b",
                        color: "#f0f0f0",
                        borderRadius: "50px",
                        padding: "15px 30px",
                        marginLeft: "23%",
                    }}
                >
                    Search
                </Button>
            </Grid>
            {results.length > 0 && (
                <>
                    <Typography
                        variant="h3"
                        align="left"
                        gutterBottom
                        style={{ color: "#00008b", marginLeft: "20px" }}
                    >
                        Results
                    </Typography>
                    {results.map((result, index) => (
                        <LawResult key={index} result={result} />
                    ))}
                </>
            )}
        </Container>
    );
}
