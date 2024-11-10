import * as React from 'react';
import { useState } from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import MenuItem from '@mui/material/MenuItem';
import { AppBar, Toolbar} from '@mui/material';
import { Link } from 'react-router-dom';
import logo from './photos/onlybuns_logo.png';
const defaultTheme = createTheme();

export default function Registracija() {
  const [ime, setIme] = useState('');
  const [prezime, setPrezime] = useState('');
  const [grad, setGrad] = useState('');
  const [drzava, setDrzava] = useState('');
  const [broj, setBroj] = useState('');
  const [brojError, setBrojError] = useState(false);
  const [email, setEmail] = useState('');
  const [emailError, setEmailError] = useState(false);
  const [password, setPassword] = useState('');
  const [repeatPassword, setRepeatPassword] = useState('');
  const [passwordMismatch, setPasswordMismatch] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [uloga, setUloga] = useState('USER');

  const validateBroj = (inputBroj) => {
    const brojRegex = /^\d{10}$/;
    return brojRegex.test(inputBroj);
  };

  const handleBrojChange = (e) => {
    const newBroj = e.target.value;
    setBroj(newBroj);
    setBrojError(!validateBroj(newBroj));
  };

  const validateEmail = (inputEmail) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(inputEmail);
  };

  const handleEmailChange = (e) => {
    const newEmail = e.target.value;
    setEmail(newEmail);
    setEmailError(!validateEmail(newEmail));
  };

  const checkEmailExists = async () => {
    try {
      const response = await fetch("http://localhost:8080/registrovaniKorisnik/emails");
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      const data = await response.json();
      return data.includes(email);
    } catch (error) {
      console.error("Error checking email:", error);
      return false;
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (password !== repeatPassword) {
      setPasswordMismatch(true);
      return;
    }

    if (!validateBroj(broj)) {
      setErrorMessage('Unesite ispravan broj telefona.');
      return;
    }

    if (!ime || !prezime || !grad || !drzava || !broj || !email || !password || !repeatPassword) {
      setErrorMessage('Podaci nisu dobro popunjeni.');
      return;
    }

    const emailExists = await checkEmailExists();
    if (emailExists) {
      setErrorMessage('Email adresa već postoji.');
      return;
    }

    const korisnik = { ime, prezime, grad, drzava, broj, email, password, uloga };
    console.log(korisnik);

    setErrorMessage('');

    fetch("http://localhost:8080/registrovaniKorisnik/add", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(korisnik)
    }).then(() => {
      console.log("Novi korisnik dodat");
    });
  };

  return (
      
    <ThemeProvider theme={defaultTheme}>
      {/* Navigation Bar */}
      <AppBar position="static" sx={{ bgcolor: '#b4a7d6' }}>
      <Toolbar sx={{ justifyContent: 'space-between' }}>
        <Link to="/" style={{ textDecoration: 'none', display: 'flex', alignItems: 'center', color: 'inherit' }}>
          <Box sx={{ display: 'flex', alignItems: 'center', ml: 2 }}>
            <img src={logo} alt="OnlyBuns Logo" style={{ height: '40px', marginRight: '10px' }} />
            <Typography variant="h5" component="div" sx={{ fontWeight: 'bold' }}>
              OnlyBuns
            </Typography>
          </Box>
        </Link>
        <Box sx={{ display: 'flex', gap: 2, mr: 2 }}>
          <Button component={Link} to="/prijava" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
            Log In
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box sx={{ marginTop: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5" sx={{ marginBottom: 2}}>
            Registration
          </Typography>
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
            <TextField fullWidth required label="First name" value={ime} onChange={(e) => setIme(e.target.value)} sx={{ mb: 1.5 }} />
            <TextField fullWidth required label="Last name" value={prezime} onChange={(e) => setPrezime(e.target.value)} sx={{ mb: 1.5 }} />
            <TextField fullWidth required label="Email" value={email} onChange={handleEmailChange} error={emailError} helperText={emailError ? 'Unesite validnu e-mail adresu' : ''} sx={{ mb: 1.5 }} />
            <TextField fullWidth required label="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} sx={{ mb: 1.5 }} />
            <TextField fullWidth required label="Repeat password" type="password" value={repeatPassword} onChange={(e) => { setRepeatPassword(e.target.value); setPasswordMismatch(false); }} sx={{ mb: 1.5 }} />
            {passwordMismatch && <Typography color="error" variant="body2" gutterBottom>Lozinke se ne podudaraju.</Typography>}
            <TextField fullWidth required label="City" value={grad} onChange={(e) => setGrad(e.target.value)} sx={{ mb: 1.5 }} />
            <TextField fullWidth required label="Country" value={drzava} onChange={(e) => setDrzava(e.target.value)} sx={{ mb: 1.5 }} />
            <TextField fullWidth required label="Phone number" value={broj} onChange={handleBrojChange} error={brojError} helperText={brojError ? 'Unesite desetocifren broj' : ''} sx={{ mb: 1.5 }} />
            <TextField select label="Role" value={uloga} onChange={(e) => setUloga(e.target.value)} fullWidth required sx={{ mb: 1.5 }}>
              <MenuItem value="ADMIN_SISTEMA">ADMIN SISTEMA</MenuItem>
              <MenuItem value="REGISTROVANI_KORISNIK">REGISTROVANI KORISNIK</MenuItem>
            </TextField>
            <Button type="submit" sx={{ padding: '5px 10px', borderRadius: '15px', fontSize: '1rem', fontWeight: 'bold', mt: 2, mb: 7 }} fullWidth variant="contained"  color="secondary">
              Sign in
            </Button>
            {errorMessage && <Typography color="error" variant="body2" gutterBottom>{errorMessage}</Typography>}
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
