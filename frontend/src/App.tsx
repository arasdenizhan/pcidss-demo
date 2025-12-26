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
import { useEffect, useState } from "react";

function App() {
  const { user, login, info, logout } = useAuth();
  const [error, setError] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    getInfo(); // eslint-disable-next-line
  }, []);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    console.log("login submitted!");
    const data = new FormData(event.currentTarget);
    const username = data.get("username") as string;
    const password = data.get("password") as string;
    if (!username || !password) {
      handleError("Username or password is missing!");
      return;
    }
    await login(username, password)
      .then((res) => {
        console.log("login successfull!");
        getInfo();
      })
      .catch((err) => {
        handleError(err.message);
        return;
      });
  };

  const getInfo = async () => {
    await info()
      .then((res) => {
        console.log("fetch info successfull!");
      })
      .catch((err) => {
        handleError(err.message);
        return;
      });
  };

  const handleLogout = async () => {
    await logout()
      .then((res) => {
        console.log("logout successfull!");
        getInfo();
      })
      .catch((err) => {
        handleError(err);
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
    return (
      <Box textAlign="center" width={750} sx={{ margin: "auto" }}>
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ m: 1 }}
          onClick={handleLogout}
        >
          Logout
        </Button>
        <PaymentCard />
      </Box>
    );
  }
}

export default App;
