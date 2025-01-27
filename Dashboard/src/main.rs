use eframe::egui;
use egui_extras::{Column, TableBuilder};
use reqwest::{header::USER_AGENT, Client};
use serde::Deserialize;

#[derive(Deserialize, Debug)]
struct Player {
    uuid: String,
    name: String,
}

#[tokio::main]
async fn main() -> Result<(), reqwest::Error> {
    let client = Client::new();
        
    let response = client
        .get("http://localhost:7070/api/players")
        .header(USER_AGENT, "rust-web-api-client")
        .send();

    let players: Vec<Player> = response.await.unwrap().json().await.unwrap();

    let native_options = eframe::NativeOptions::default();
    drop(eframe::run_native("Quick Dash", native_options, Box::new(|cc| Ok(
        Box::new(QuickDash::new(cc, players))
    ))));
    Ok(())
}

struct QuickDash {
    players: Vec<Player>,
}

impl QuickDash {
    fn new(_cc: &eframe::CreationContext<'_>, players: Vec<Player>) -> Self {
        Self {
            players
        }
    }
}

impl eframe::App for QuickDash {
    fn update(&mut self, ctx: &egui::Context, _frame: &mut eframe::Frame) {
        egui::CentralPanel::default().show(ctx, |ui| {
            let table = TableBuilder::new(ui)
                .column(Column::auto())
                .column(Column::auto());

            table.header(20.0, |mut header| {
                header.col(|ui| {
                    ui.strong("Player");
                });
                header.col(|ui| {
                    ui.strong("UUID");
                });
            })
            .body(|mut body| {
                body.row(18.0, |mut row| {
                    row.col(|ui| {
                        ui.label(&self.players[0].name);
                    });
                    row.col(|ui| {
                        ui.label(&self.players[0].uuid);
                    });
                });
            });
        });
    }
}
