import {
    Container,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    OutlinedInput,
    FormControlLabel,
    Button,
    Checkbox,
} from "@mui/material";
import { useState, forwardRef, useImperativeHandle } from "react";

const ContractSearch = forwardRef(({ onAdd, onOr, index }, ref) => {
    const [currentField, setCurrentField] = useState("");
    const [inputValue, setInputValue] = useState("");
    const [isPhrase, setIsPhrase] = useState(false);
    const [isNotOperator, setIsNotOperator] = useState(false);

    useImperativeHandle(ref, () => ({
        getCurrentValues: () => ({
          currentField,
          currentValue: inputValue,
          isPhrase,
          isNotOperator,
        }),
      }));

    const onSelectionChange = (event) => {
        setCurrentField(event.target.value);
    };

    const phraseChange = () => {
        setIsPhrase(!isPhrase);
    };

    const operatorChange = () => {
        setIsNotOperator(!isNotOperator);
    };

    return (
        <Container
            maxWidth="100%"
            style={{
                display: "flex",
                flexDirection: "row",
                alignItems: "center",
            }}
        >
            <FormControl
                fullWidth
                variant="outlined"
                margin="normal"
                style={{ marginRight: "10px", width: "300px" }}
            >
                <InputLabel id="select-option-label">Select Option</InputLabel>
                <Select
                    label="Select Option"
                    labelId={`select-option-label-${index}`}
                    style={{ color: "#cccccc" }}
                    value={currentField}
                    onChange={onSelectionChange}
                >
                    <MenuItem value="signatoryPersonName">
                        Signatory Person Name
                    </MenuItem>
                    <MenuItem value="signatoryPersonSurname">
                        Signatory Person LastName
                    </MenuItem>
                    <MenuItem value="governmentName">Government Name</MenuItem>
                    <MenuItem value="governmentLevel">Government Level</MenuItem>
                    <MenuItem value="content">Content</MenuItem>
                </Select>
            </FormControl>
            <FormControl
                fullWidth
                variant="outlined"
                margin="normal"
                style={{ marginRight: "10px", width: "400px" }}
            >
                <InputLabel htmlFor="input-field">Input Field</InputLabel>
                <OutlinedInput
                    id={`input-field-${index}`}
                    label="Input Field"
                    value={inputValue}
                    onChange={(e) => setInputValue(e.target.value)}
                    style={{ color: "#cccccc" }}
                />
            </FormControl>

            <FormControl
                component="fieldset"
                margin="normal"
                color="inherit"
                style={{ marginRight: "10px", color: "#cccccc" }}
            >
                <FormControlLabel
                    control={
                        <Checkbox
                            style={{ color: "#cccccc" }}
                            checked={isPhrase}
                            onChange={phraseChange}
                        />
                    }
                    label="isPhrase"
                />
            </FormControl>

            <FormControl
                component="fieldset"
                margin="normal"
                style={{ marginRight: "10px", color: "#cccccc" }}
            >
                <FormControlLabel
                    control={
                        <Checkbox
                            style={{ color: "#cccccc" }}
                            checked={isNotOperator}
                            onChange={operatorChange}
                        />
                    }
                    label="NOT"
                />
            </FormControl>

            <FormControl margin="normal">
                <Button
                    variant="contained"
                    style={{ backgroundColor: "#2E7D32", color: "#f0f0f0" }}
                    onClick={onAdd}
                >
                    AND
                </Button>
            </FormControl>
            <FormControl margin="normal" style={{ marginLeft: "10px" }}>
                <Button
                    variant="contained"
                    style={{ backgroundColor: "#B71C1C", color: "#f0f0f0" }}
                    onClick={onOr}
                >
                    OR
                </Button>
            </FormControl>
        </Container>
    );
});

export default ContractSearch;
