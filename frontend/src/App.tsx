import {
  Alert,
  Box,
  Button,
  Card,
  CardContent,
  FormControl,
  FormLabel,
  Grid,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import { useAuth } from "./Auth";
import PaymentCard from "./PaymentCard";
import { useState } from "react";

function App() {
  const { user, login, logout } = useAuth();
  const [error, setError] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    console.log("login submitted!");
    const data = new FormData(event.currentTarget);
    const username = data.get("username") as string;
    const password = data.get("password") as string;
    if (!username || !password) {
      handleError("Username or password is missing!");
      return;
    }
    login(username, password)
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        handleError(err.message);
        return;
      });
  };

  const handleError = (message: string) => {
    setError(true);
    setErrorMessage(message);
    setTimeout(() => {
      setError(false);
      setErrorMessage("");
    }, 2000);
  };

  if (!user) {
    return (
      <Grid
        container
        justifyContent="center"
        alignItems="center"
        minHeight="100vh"
      >
        <Card variant="outlined" sx={{ width: "50%" }}>
          <CardContent>
            <Stack spacing={2}>
              <Typography component="h1" variant="h4" sx={{ width: "100%" }}>
                Sign in
              </Typography>
              {error ? (
                <Alert variant="filled" severity="error">
                  {errorMessage}
                </Alert>
              ) : (
                <></>
              )}
              <Box
                component="form"
                onSubmit={handleSubmit}
                sx={{
                  display: "flex",
                  flexDirection: "column",
                  width: "100%",
                  gap: 2,
                }}
              >
                <FormControl>
                  <FormLabel htmlFor="username">Username</FormLabel>
                  <TextField
                    id="username"
                    type="username"
                    name="username"
                    placeholder="your username"
                    autoFocus
                    required
                    fullWidth
                    variant="outlined"
                  />
                </FormControl>
                <FormControl>
                  <FormLabel htmlFor="password">Password</FormLabel>
                  <TextField
                    name="password"
                    placeholder="••••••"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    autoFocus
                    required
                    fullWidth
                    variant="outlined"
                  />
                </FormControl>
                <Button type="submit" fullWidth variant="contained">
                  Sign in
                </Button>
              </Box>
            </Stack>
          </CardContent>
        </Card>
      </Grid>
    );
  } else {
    return <PaymentCard />;
  }
}

export default App;
