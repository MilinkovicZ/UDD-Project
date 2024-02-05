import { Typography, Container, Button, Box } from "@mui/material";
import { Link } from "react-router-dom";
import ContractSearch from "./ContractSearch";
import React from "react";
import { useState, useRef } from "react";
import parseFilters from "../../services/QueryParserService";
import { searchContracts } from "../../services/SearchService";

export default function ContractPage() {
    const contractSearchRefs = useRef([]);
    const [filters, setFilters] = useState([
        {
            operator: "",
            field: "",
            value: "",
            isPhrase: false,
            isNot: false,
            id: 0,
        },
    ]);

    const onAndClick = () => {
        addFilter("AND");
    };

    const onOrClick = () => {
        addFilter("OR");
    };

    const addFilter = (operator) => {
        setFilters((prevFilters) => [
            ...prevFilters,
            {
                operator: operator,
                field: "",
                value: "",
                isPhrase: false,
                isNot: false,
                id: prevFilters.length,
            },
        ]);
        contractSearchRefs.current.push(React.createRef());
    };

    const handleOnSearch = () => {
        contractSearchRefs.current.forEach((contractSearchRef, index) => {
            const currentValues = contractSearchRef.getCurrentValues();
            const filterToUpdate = filters.find(
                (filter) => filter.id === index
            );

            if (filterToUpdate) {
                filterToUpdate.field = currentValues.currentField;
                filterToUpdate.value = currentValues.currentValue;
                filterToUpdate.isPhrase = currentValues.isPhrase;
                filterToUpdate.isNot = currentValues.isNotOperator;
            } else {
                console.warn(`No filter found with id ${index}`);
            }
        });

        let query = parseFilters(filters);
        const results = searchContracts(query);
        console.log(results);
    };

    return (
        <Container
            maxWidth="100%"
            style={{ marginTop: "20px", marginLeft: "20px", paddingLeft: "0" }}
        >
            <div
                style={{
                    paddingLeft: "20px",
                    paddingRight: "40px",
                    display: "flex",
                    justifyContent: "space-between",
                }}
            >
                <Typography
                    variant="h3"
                    align="left"
                    gutterBottom
                    style={{ color: "#00008b" }}
                >
                    Search Contracts
                </Typography>
                <Button
                    color="inherit"
                    component={Link}
                    to="/contracts/location"
                    style={{
                        backgroundColor: "#141e30",
                        color: "#f0f0f0",
                        height: "40px"
                    }}
                >
                    Location
                </Button>
            </div>
            {filters.map((filter, index) => (
                <Box key={filter.id} marginBottom="10px">
                    {index > 0 && (
                        <div
                            style={{
                                color: "#ffffff",
                                fontSize: "1.5em",
                                marginLeft: "23px",
                            }}
                        >
                            {filter.operator}
                        </div>
                    )}
                    <ContractSearch
                        index={index}
                        onAdd={onAndClick}
                        onOr={onOrClick}
                        ref={(ref) => (contractSearchRefs.current[index] = ref)}
                    />
                </Box>
            ))}
            <Button
                variant="contained"
                onClick={handleOnSearch}
                style={{
                    backgroundColor: "#00008b",
                    color: "#f0f0f0",
                    borderRadius: "50px",
                    padding: "15px 30px",
                    marginTop: "20px",
                    marginLeft: "500px",
                }}
            >
                Search
            </Button>
        </Container>
    );
}
