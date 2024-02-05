import { useState } from "react";
import {
    Container,
    Typography,
    Button,
    FormControl,
    InputLabel,
    OutlinedInput,
    Grid
} from "@mui/material";
import { searchLaws } from "../../services/SearchService";

export default function LawPage() {
    const [inputValue, setInputValue] = useState("");

    const handleOnSearch = () => {
        if (!inputValue.trim()) {
            setInputValue("");            
            alert('Content is required!');
            return;
        }
        
       const results = searchLaws(inputValue);
       console.log(results);
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

            <Grid
                container
                style={{ marginTop: "20px" }}
            >
                <Button
                    variant="contained"
                    onClick={handleOnSearch}
                    style={{
                        backgroundColor: "#00008b",
                        color: "#f0f0f0",
                        borderRadius: "50px",
                        padding: "15px 30px",
                        marginLeft: "23%"
                    }}
                >
                    Search
                </Button>
            </Grid>
        </Container>
    );
}
