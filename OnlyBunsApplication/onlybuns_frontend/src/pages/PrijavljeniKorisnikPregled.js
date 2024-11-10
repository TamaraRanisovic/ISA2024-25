import React, { useEffect, useState } from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link } from 'react-router-dom';
import logo from './photos/onlybuns_logo.png';

const PrijavljeniKorisnikPregled = () => {
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [role, setRole] = useState('');
  const token = localStorage.getItem('jwtToken'); // Get JWT token from localStorage

  // Function to decode the JWT token by calling the backend endpoint
  useEffect(() => {
    if (token) {
      // Send the token in the body of the POST request (not in the Authorization header)
      fetch('http://localhost:8080/auth/decodeJwt', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // Indicate that we're sending JSON data
        },
        body: token // Send the token in the request body
      })
        .then(response => response.json())
        .then(data => {
          // Assuming the response contains 'email' and 'role' from the decoded token
          if (data) {
            setEmail(data.Email); // Set email from the response
            setName(data.Name); // Set email from the response
            setRole(data.Role);   // Set role from the response
          }
        })
        .catch(error => {
          console.error('Error decoding JWT token:', error);
        });
    }
  }, [token]);

  return (
    <div>
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
          <Box sx={{ display: 'flex', gap: 2, mr: 2, alignItems: 'center' }}>
            {token && name ? ( 
              <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                Welcome, {name}
              </Typography>
            ) : (
              <>
                <Button component={Link} to="/registracija" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
                  Sign In
                </Button>
                <Button component={Link} to="/prijava" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
                  Log In
                </Button>
              </>
            )}
          </Box>
        </Toolbar>
      </AppBar>
    </div>
  );
};

export default PrijavljeniKorisnikPregled;
