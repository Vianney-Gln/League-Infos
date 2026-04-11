import { Component, DestroyRef, inject, Inject, OnInit, signal, WritableSignal } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CommentaryService } from '../../services/commentary/commentary.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CommentaryModalData } from '../../common/models/modal/CommentaryModalData';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { CommentaryDTO } from '../../common/models/CommentaryDTO';
import { GET_COMMENTARY_ERROR } from '../../common/constants/errors';

@Component({
  selector: 'app-commentary',
  standalone: true,
  imports: [],
  templateUrl: './commentary.component.html',
  styleUrl: './commentary.component.scss',
})
export class CommentaryComponent implements OnInit {
  constructor(
    private dialog: MatDialog,
    private commentaryService: CommentaryService,
    @Inject(MAT_DIALOG_DATA) public data: CommentaryModalData,
  ) {}

  destroyedRef = inject(DestroyRef);
  commentaryDTOSignal: WritableSignal<CommentaryDTO | null> = signal(null);
  isLoading: WritableSignal<boolean> = signal(false);
  commentaryError: WritableSignal<string> = signal(GET_COMMENTARY_ERROR);
  getCommentaryError: WritableSignal<boolean> = signal(false);

  ngOnInit(): void {
    this.isLoading.set(true);
    this.commentaryService
      .getCommentary(this.data.matchId, this.data.gameId, this.data.puuid, this.data.pseudo)
      .pipe(takeUntilDestroyed(this.destroyedRef))
      .subscribe({
        next: (res) => {
          this.commentaryDTOSignal.set(res);
          this.isLoading.set(false);
        },
        error: (err) => {
          console.error('Erreur lors de la récupération du commentaire:', err);
          this.isLoading.set(false);
          this.getCommentaryError.set(true);
        },
      });
  }

  onCloseModal(): void {
    this.dialog.closeAll();
  }
}
