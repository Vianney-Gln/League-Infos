import { Component, computed, OnDestroy, OnInit, signal, WritableSignal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { HistoryService } from '../../services/games-history/history.service';
import { MatchDTO, ParticipantMatchDTO, TeamDTO } from '../../common/models/games-history/matchDTO';
import { HistoryItemsComponent } from '../../games-history/history-items/history-items.component';
import { CommonModule } from '@angular/common';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-game-detail',
  standalone: true,
  imports: [HistoryItemsComponent, CommonModule],
  templateUrl: './game-detail.component.html',
  styleUrl: './game-detail.component.scss',
})
export class GameDetailComponent implements OnInit, OnDestroy {
  constructor(private historyService: HistoryService, private route: ActivatedRoute, private router: Router, private location: Location) {}

  private destroy$ = new Subject<void>();
  currentMatchSignal: WritableSignal<MatchDTO | undefined> = signal(undefined);
  listParticipantsTeam1Signal: WritableSignal<ParticipantMatchDTO[] | undefined> = signal([]);
  listParticipantsTeam2Signal: WritableSignal<ParticipantMatchDTO[] | undefined> = signal([]);
  team1Signal = computed(() => this.currentMatchSignal()?.info.teams.find((team: TeamDTO) => team.teamId === 100));
  team2Signal = computed(() => this.currentMatchSignal()?.info.teams.find((team: TeamDTO) => team.teamId === 200));

  totalKillsTeam1 = computed(() => this.calculateTotalKillsByTeam(this.listParticipantsTeam1Signal()));
  totalKillsTeam2 = computed(() => this.calculateTotalKillsByTeam(this.listParticipantsTeam2Signal()));

  totalAssistsTeam1 = computed(() => this.calculateTotalAssistsByTeam(this.listParticipantsTeam1Signal()));
  totalAssistsTeam2 = computed(() => this.calculateTotalAssistsByTeam(this.listParticipantsTeam2Signal()));

  totalDeathsTeam1 = computed(() => this.calculateTotalDeathsByTeam(this.listParticipantsTeam1Signal()));
  totalDeathsTeam2 = computed(() => this.calculateTotalDeathsByTeam(this.listParticipantsTeam2Signal()));

  totalGoldEarnedTeam1 = computed(() => this.calculateTotalGoldEarnedByTeam(this.listParticipantsTeam1Signal()));
  totalGoldEarnedTeam2 = computed(() => this.calculateTotalGoldEarnedByTeam(this.listParticipantsTeam2Signal()));

  ngOnInit(): void {
    if (!this.currentMatchSignal()) {
      this.getGameDetailByGameId();
    }
  }

  private getGameDetailByGameId() {
    const matchId: string | null = this.route.snapshot.paramMap.get('matchId');
    const gameId: string | undefined = matchId?.split('_')[1];
    if (gameId) {
      return this.historyService
        .getGameDetailHistoryByGameId(Number(gameId))
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (res) => {
            this.currentMatchSignal.set(res);
            this.dispatchParticipantStatsByTeam();
          },
          error: (err) => console.log(err),
        });
    }
  }

  private dispatchParticipantStatsByTeam() {
    this.listParticipantsTeam1Signal.set(this.currentMatchSignal()?.info.participants.filter((part) => part.teamId === 100));
    this.listParticipantsTeam2Signal.set(this.currentMatchSignal()?.info.participants.filter((part) => part.teamId === 200));
  }

  private calculateTotalKillsByTeam(listParticipants: ParticipantMatchDTO[] | undefined) {
    return listParticipants?.reduce((prev, curr) => {
      return prev + curr.kills;
    }, 0);
  }

  private calculateTotalAssistsByTeam(listParticipants: ParticipantMatchDTO[] | undefined) {
    return listParticipants?.reduce((prev, curr) => {
      return prev + curr.assists;
    }, 0);
  }

  private calculateTotalDeathsByTeam(listParticipants: ParticipantMatchDTO[] | undefined) {
    return listParticipants?.reduce((prev, curr) => {
      return prev + curr.deaths;
    }, 0);
  }

  private calculateTotalGoldEarnedByTeam(listParticipants: ParticipantMatchDTO[] | undefined) {
    return listParticipants?.reduce((prev, curr) => {
      return prev + curr.goldEarned;
    }, 0);
  }

  goBack() {
    const from = history.state?.from;

    if (from) {
      this.router.navigateByUrl(from);
    } else {
      this.location.back();
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
