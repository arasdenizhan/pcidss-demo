import { Box, Paper, Typography } from "@mui/material";
import CancelIcon from "@mui/icons-material/Cancel";

export default function PaymentFailed(failReason: string) {
  return (
    <Box
      sx={{
        minHeight: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#f5f5f5",
      }}
    >
      <Paper
        elevation={3}
        sx={{
          p: 6,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          borderRadius: 3,
        }}
      >
        <CancelIcon sx={{ fontSize: 80, color: "red", mb: 2 }} />
        <Typography variant="h4" component="h1" gutterBottom>
          Payment Failed
        </Typography>
        <Typography variant="body1" color="textSecondary">
          Failure reason = {failReason}. Please try again!
        </Typography>
      </Paper>
    </Box>
  );
}
