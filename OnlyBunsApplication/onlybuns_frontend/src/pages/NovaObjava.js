import * as React from 'react';
import { useEffect, useState, useRef } from 'react';
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
import { Link, useNavigate } from 'react-router-dom';
import logo from './photos/onlybuns_logo.png';
import { MapContainer, TileLayer, Marker, useMapEvents } from 'react-leaflet';


const defaultTheme = createTheme();

export default function NovaObjava() {
  const [opis, setOpis] = useState('');
  const [g_sirina, setG_sirina] = useState('');
  const [g_duzina, setG_duzina] = useState('');
  const [slika, setSlika] = useState('dasdas');
  const [datum_objave, setDatumObjave] = useState(new Date().toISOString());

  const [korisnicko_ime, setKorisnickoIme] = useState('asasa');

  const [errorMessage, setErrorMessage] = useState('');
  const token = localStorage.getItem('jwtToken'); // Get JWT token from localStorage
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [role, setRole] = useState('');
  const formData = new FormData();
  const [message, setMessage] = useState('');
  const [file, setFile] = useState(null);
  const [selectedPosition, setSelectedPosition] = useState(null);
  const navigate = useNavigate();
  const [successMessage, setSuccessMessage] = useState('');
  const isMounted = useRef(true);



    

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
            setKorisnickoIme(data.Username); // Set email from the response
            setRole(data.Role);   // Set role from the response
          }
        })
        .catch(error => {
          console.error('Error decoding JWT token:', error);
        });
    }
  }, [token]);

  
  const handlePhotoChange = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile) {
      setFile(selectedFile);
      setSlika(selectedFile.name); // Set `slika` to the file name for reference
    }
  };

  const handlePhotoUpload = async () => {
    const formData = new FormData();
    formData.append('file', file);
    
    try {
      const response = await fetch("http://localhost:8080/images/upload", {
        method: "POST",
        body: formData
      });
      
      if (response.ok) {
        console.log("Photo uploaded successfully");
      } else {
        console.error("Photo upload failed");
      }
    } catch (error) {
      console.error("Error uploading photo:", error);
    }
  };

  const LocationMarker = () => {
    useMapEvents({
      click(event) {
        const { lat, lng } = event.latlng;
        setG_sirina(lat);
        setG_duzina(lng);
        setSelectedPosition([lat, lng]);
      },
    });
    return selectedPosition ? (
      <Marker position={selectedPosition} />
    ) : null;
  };

  useEffect(() => {
    isMounted.current = true;
    return () => {
      isMounted.current = false;
    };
  }, []);


  const handleSubmit = async (event) => {
    event.preventDefault();
    
    if (!korisnicko_ime || !opis || !g_sirina || !g_duzina || !slika) {
      setErrorMessage('Enter valid data.');
      return;
    }
    setErrorMessage('');

    setDatumObjave(new Date().toISOString());
    const objava = { korisnicko_ime, g_sirina, opis, g_duzina, slika, datum_objave };
    
    try {
      const response = await fetch("http://localhost:8080/objava/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(objava)
      });

      if (response.ok) {
        console.log("New post created successfully");
        
        // Now, upload the photo after the objava is created
        await handlePhotoUpload();

        setSuccessMessage("New post created successfully. Redirecting to feed...");
  
        setTimeout(() => {
          if (isMounted.current) {
            setSuccessMessage("");
            navigate("/prijavljeniKorisnikPregled");
          }
        }, 15000);
      } else {
        console.error("Failed to create post");
      }
    } catch (error) {
      console.error("Error creating post:", error);
    }
  };



  return (
      
    <ThemeProvider theme={defaultTheme}>
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
          <Box sx={{ display: 'flex', gap: 2 }}>
            <Button component={Link} to="/prijavljeniKorisnikPregled" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Feed
            </Button>
            <Button component={Link} to="/novaObjava" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              New post
            </Button>
            <Button component={Link} to="/shop" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Trends
            </Button>
            <Button component={Link} to="/about" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Nearby Posts
            </Button>
            <Button component={Link} to="/contact" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              Chat
            </Button>
            <Button component={Link} to="/contact" color="inherit" variant="outlined" sx={{ borderRadius: '20px', fontWeight: 'bold' }}>
              My profile
            </Button>
          </Box>
          <Box sx={{ display: 'flex', gap: 2, mr: 2, alignItems: 'center' }}>
            {token && korisnicko_ime ? ( 
              <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
                Welcome, {korisnicko_ime}
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
      <Container component="main" maxWidth="xs">
      <CssBaseline />
      <Box sx={{ marginTop: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <Typography component="h1" variant="h5" sx={{ marginBottom: 2 }}>
          Create New Post
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextField
            fullWidth
            required
            label="Description"
            value={opis}
            onChange={(e) => setOpis(e.target.value)}
            sx={{ mb: 1.5 }}
          />
          <Typography variant="body2" sx={{ mb: 1 }}>
            Select location on the map or enter coordinates manually.
          </Typography>
          <MapContainer
            center={[45.2671, 19.8335]} // Centered on Novi Sad by default
            zoom={13}
            style={{ height: '200px', width: '100%', marginBottom: '15px' }}
          >
            <TileLayer
              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
              attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            />
            <LocationMarker />
          </MapContainer>
          <TextField
            fullWidth
            label="Latitude"
            value={g_sirina}
            onChange={(e) => setG_sirina(e.target.value)}
            sx={{ mb: 1.5 }}
          />
          <TextField
            fullWidth
            label="Longitude"
            value={g_duzina}
            onChange={(e) => setG_duzina(e.target.value)}
            sx={{ mb: 1.5 }}
          />
          <div style={{ marginBottom: '15px' }}>
            <label>Upload Photo:</label>
            <input type="file" accept="image/*" onChange={handlePhotoChange} />
          </div>
          <Button
            type="submit"
            sx={{ padding: '5px 10px', borderRadius: '15px', fontSize: '1rem', fontWeight: 'bold', mt: 2, mb: 7 }}
            fullWidth
            variant="contained"
            color="secondary"
          >
            Publish
          </Button>
          {errorMessage && (
            <Typography color="error" sx={{ mb: 3 }} variant="body2" gutterBottom>
              {errorMessage}
            </Typography>
          )}
          {successMessage && (
        <div style={{ color: 'green', marginTop: '7px',  marginBottom: '3px' }}>
          {successMessage}
        </div>
      )}
        </Box>
      </Box>
    </Container>
    </ThemeProvider>
  );
}
