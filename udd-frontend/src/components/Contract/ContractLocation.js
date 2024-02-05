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
import { searchContractsByLocation } from "../../services/SearchService";
import { Link } from "react-router-dom";

export default function LawPage() {
    const [address, setAddress] = useState("");
    const [radius, setRadius] = useState("");

    const handleOnSearch = () => {
        if (!radius.trim() || radius <= 0 || !address.trim()) {
            setRadius("");
            setAddress("");
            alert('Address is required!');
            return;
        }
        
        let locationData = {
            address: address,
            radius: radius
        }

       const results = searchContractsByLocation(locationData);
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
                    Search Contracts by location
                </Typography>
                <Button
                    color="inherit"
                    component={Link}
                    to="/contracts"
                    style={{
                        backgroundColor: "#141e30",
                        color: "#f0f0f0",
                        height: "40px"
                    }}
                >
                    Advanced
                </Button>
            </div>

            <FormControl
                fullWidth
                variant="outlined"
                style={{ marginLeft: "20px", width: "50%" }}
            >
                <InputLabel htmlFor="address-field">Address (33, Petra Drapšina, Novi Sad, Srbija)</InputLabel>
                <OutlinedInput
                    id={`address-input`}
                    label="Address Field"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    style={{ color: "#cccccc" }}
                />
            </FormControl>

            <FormControl
                fullWidth
                variant="outlined"
                style={{ marginLeft: "20px", width: "10%" }}
            >
                <InputLabel htmlFor="radius-field">Radius (km)</InputLabel>
                <OutlinedInput
                    id={`radius-field`}
                    label="Radius Field"
                    type="number"
                    value={radius}
                    onChange={(e) => setRadius(e.target.value)}
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
