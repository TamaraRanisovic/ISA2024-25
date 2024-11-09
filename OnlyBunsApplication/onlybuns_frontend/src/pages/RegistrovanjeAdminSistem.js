import * as React from 'react';
import{ useState } from 'react';
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


const defaultTheme = createTheme();

export default function RegistrovanjeAdminSistem() {
  const[ime,setIme]=useState('')
  const[prezime,setPrezime]=useState('')
  const[grad,setGrad]=useState('')
  const[drzava,setDrzava]=useState('')
  const[broj,setBroj]=useState('')
  const [brojError, setBrojError] = useState(false);
  const[email,setEmail]=useState('')
  const [emailError, setEmailError] = useState(false);
  const[password,setPassword]=useState('')
  const [repeatPassword, setRepeatPassword] = useState(''); // Dodato stanje za ponovljenu lozinku
  const [passwordMismatch, setPasswordMismatch] = useState(false); // Dodato stanje za praćenje neuspešnog podudaranja
  const [errorMessage, setErrorMessage] = useState('');
  const [uloga, setUloga] = useState('USER'); // Default role

  const validateBroj = (inputBroj) => {
    const brojRegex = /^\d{10}$/; // Regex za desetocifren broj
    return brojRegex.test(inputBroj);
  };
  const handleBrojChange = (e) => {
    const newBroj = e.target.value;
    setBroj(newBroj);
  
    // Provera formata broja telefona
    setBrojError(!validateBroj(newBroj));
  };

  // Funkcija za proveru formata e-mail adrese
  const validateEmail = (inputEmail) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Regex za proveru formata e-mail adrese
    return emailRegex.test(inputEmail);
  };
  // Event handler za promenu vrednosti e-mail adrese
  const handleEmailChange = (e) => {
    const newEmail = e.target.value;
    setEmail(newEmail);
  
    // Provera formata e-mail adrese
    setEmailError(!validateEmail(newEmail));
  };  

  const checkEmailExists = async () => {
    try {
      const response = await fetch("http://localhost:8080/adminsistem/emails");

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
    // Provera podudaranja lozinki
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
    const korisnik = {ime, prezime, grad, drzava, broj, email, password, uloga}
    console.log(korisnik);
    
    setErrorMessage('');


    fetch("http://localhost:8080/adminsistem/save",{
      method:"POST",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify(korisnik)

     }).then(()=>{
        console.log("Novi korisnik dodat")
     })
};

  return (
    <ThemeProvider theme={defaultTheme}>
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <Box sx={{ marginTop: 8, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Registruj se
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextField fullWidth required label="Ime" value={ime} onChange={(e) => setIme(e.target.value)}  sx={{ mb: 1.5 }} />
          <TextField fullWidth required label="Prezime" value={prezime} onChange={(e) => setPrezime(e.target.value)}  sx={{ mb: 1.5 }}/>
          <TextField fullWidth required label="Email" value={email} onChange={handleEmailChange} error={emailError} helperText={emailError ? 'Unesite validnu e-mail adresu' : ''}  sx={{ mb: 1.5 }}/>
          <TextField fullWidth required label="Lozinka" type="password" value={password} onChange={(e) => setPassword(e.target.value)}  sx={{ mb: 1.5 }} />
          <TextField fullWidth required label="Ponovi Lozinku" type="password" value={repeatPassword} onChange={(e) => { setRepeatPassword(e.target.value); setPasswordMismatch(false); }}  sx={{ mb: 1.5 }}/>
          {passwordMismatch && <Typography color="error" variant="body2" gutterBottom>Lozinke se ne podudaraju.</Typography>}
          <TextField fullWidth required label="Grad" value={grad} onChange={(e) => setGrad(e.target.value)}  sx={{ mb: 1.5 }}/>
          <TextField fullWidth required label="Drzava" value={drzava} onChange={(e) => setDrzava(e.target.value)}  sx={{ mb: 1.5 }}/>
          <TextField fullWidth required label="Broj Telefona" value={broj} onChange={handleBrojChange} error={brojError} helperText={brojError ? 'Unesite desetocifren broj' : ''} sx={{ mb: 1.5 }} />
          <TextField select label="Uloga" value={uloga} onChange={(e) => setUloga(e.target.value)} fullWidth required  sx={{ mb: 1.5 }}>
            <MenuItem value="ADMIN_SISTEMA">ADMIN SISTEMA</MenuItem>
            <MenuItem value="REGISTROVANI_KORISNIK">REGISTROVANI KORISNIK</MenuItem>
          </TextField>
          <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
            Registruj se
          </Button>
          {errorMessage && <Typography color="error" variant="body2" gutterBottom>{errorMessage}</Typography>}
        </Box>
      </Box>
    </Container>
  </ThemeProvider>
  );
}