
import React, { useState } from 'react';
import FileList from './FileList';
import FileUpload from './FileUpload';
import FileViewer from './FileViewer';
import { Container, Paper, Typography } from '@mui/material';

export default function App() {
  const [viewing, setViewing] = useState(null);
  const [refresh, setRefresh] = useState(0);

  const handleView = (filename) => setViewing(filename);
  const handleBack = () => setViewing(null);
  const handleUpload = () => setRefresh(r => r + 1);

  return (
    <Container maxWidth="sm" sx={{mt:4}}>
      <Paper sx={{p:3}}>
        <Typography variant="h4" align="center" gutterBottom>Typeface Dropbox</Typography>
        {!viewing && <>
          <FileUpload onUpload={handleUpload} />
          <FileList onView={handleView} key={refresh} />
        </>}
        {viewing && <FileViewer filename={viewing} onBack={handleBack} />}
      </Paper>
    </Container>
  );
}
